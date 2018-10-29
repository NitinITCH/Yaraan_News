package com.app.yaraan.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.R;
import com.app.yaraan.retrofit.RestClient;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerification extends Activity implements View.OnClickListener{

    EditText edtOne,edtTwo,edtThree,edtFour;

    String editone,edittwo,editthree,editfour;

    TextView resend;
    Button btnOtp;

    SharedPreferences sharedPreferences;

    String userid;
    String allotp;
    String email_id;
    String veryfiEmail;
    String mainEmail;
    Bundle bundle=new Bundle();

    public static String TAG=OtpVerification.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        Bundle bundle=getIntent().getExtras();

        mainEmail=bundle.getString("emailLogin");

        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
      userid=  sharedPreferences.getString("userid","");
      email_id=sharedPreferences.getString("useremail","");
      veryfiEmail=sharedPreferences.getString("veryfiEmail","");

     Log.e(TAG,"shareUser"+userid);
      resend=(TextView)findViewById(R.id.resend);

      btnOtp=(Button)findViewById(R.id.btnOtp);

        edtOne=(EditText)findViewById(R.id.edtOne);
        edtTwo=(EditText)findViewById(R.id.edtTwo);
        edtThree=(EditText)findViewById(R.id.edtThree);
        edtFour=(EditText)findViewById(R.id.edtFour);
        edtOne.setEnabled(true);
        edtTwo.setEnabled(true);
        edtThree.setEnabled(true);
        edtFour.setEnabled(true);
        edtOne.requestFocus();
        edtOne.setFocusableInTouchMode(true);
        resend.setOnClickListener(this);
        btnOtp.setOnClickListener(this);


    }

    public void getEditext(){
        editone=edtOne.getText().toString().trim();
        edittwo=edtTwo.getText().toString().trim();
        editthree=edtThree.getText().toString().trim();
        editfour=edtFour.getText().toString().trim();
        allotp=editone+edittwo+editthree+editfour;
        makeOtp();

    }


    public void otpRecieve(String userid,String otp){

        RestClient.getServices().otpR(userid,otp).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {

                    String jsonString=response.body().string();

                    String url=jsonString;
                    Log.e(TAG,"jsonString"+jsonString);
                    if(!TextUtils.isEmpty(jsonString)){
                        JSONObject jsonObject=new JSONObject(jsonString);
                        Log.e("otpVerification",jsonObject.toString());
                        int sucess=jsonObject.getInt("success");

                        if(sucess==1){
                            String userid = jsonObject.getString("user_id");
                            String user_email=jsonObject.getString("user_email");
                                SharedPreferences sharedPreferences =getSharedPreferences(Constants.KEY, MODE_PRIVATE);
                                SharedPreferences.Editor edt = sharedPreferences.edit();
                                edt.putString("userid",userid);
                                edt.putString("user_email",user_email);
                                edt.apply();

                                Intent intent = new Intent(OtpVerification.this, LandingAct.class);
                                startActivity(intent);
                                finish();


                        }else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(OtpVerification.this)
                                            .setMessage("Invalid Otp, Please Try Again")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            }).show();


                                }
                            });
                        }


                    }
                }catch (Exception e) {
                    DialogUtils.cancleProgressDialog();
                    e.printStackTrace();
                    Log.e(TAG,"jsonError "+e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                DialogUtils.cancleProgressDialog();
            }
        });

    }

    void makeOtp() {
        allotp = editone + edittwo + editthree + editfour;
        if (!allotp.equalsIgnoreCase("") && allotp.length() == 4) {
            otpRecieve(mainEmail,allotp);
        } else {
            Toast.makeText(OtpVerification.this,"please enter valid otp number",Toast.LENGTH_LONG).show();
        }
    }

    public void resendOtp(String email){

        DialogUtils.showProgressDialog(OtpVerification.this,"Please wait..");

        RestClient.getServices().orpResend(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String jsonString=response.body().string();
                    if(!TextUtils.isEmpty(jsonString)){

                        JSONObject jsonObject1=new JSONObject(jsonString);

                        int sucess=jsonObject1.getInt("success");
                        if(sucess==1){

                            ///change something.....

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(OtpVerification.this)
                                            .setMessage("Resend Successfully, Please Check your Email-id")
                                            .setCancelable(false)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            }).show();
                                }
                            });
                            

                        }else {

                            Toast.makeText(OtpVerification.this,"Wrong email-id",Toast.LENGTH_LONG).show();
                        }

                    }


                    DialogUtils.cancleProgressDialog();
                }catch (Exception e){
                    DialogUtils.cancleProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.resend:
                resendOtp(veryfiEmail);
                break;
            case R.id.btnOtp:
                getEditext();

                break;
        }
    }
}
