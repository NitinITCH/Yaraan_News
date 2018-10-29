package com.app.yaraan.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.yaraan.R;
import com.app.yaraan.retrofit.RestClient;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginVerification extends AppCompatActivity implements View.OnClickListener {

    private EditText edtVerifyEmail;
    private Button btnLogin;
    private String userEmailid;
    private String getToken;
    String androidLogin="Android";
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verification);

        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        getToken= FirebaseInstanceId.getInstance().getToken();
        edtVerifyEmail=(EditText)findViewById(R.id.edtVerifyEmail);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
    }



    public void verification(final String email, String token, String loginOs){

        RestClient.getServices().emailVerification(email,token,loginOs).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);

                    Log.e("LoginVerification",jsonObject.toString());

                    int success=jsonObject.getInt("success");
                    String message=jsonObject.getString("message");

                    if(!message.equals("please enter valid email")){

                        DialogUtils.showProgressDialog(LoginVerification.this,"Verified..");
                        Intent intent=new Intent(LoginVerification.this,OtpVerification.class);
                        intent.putExtra("emailLogin",email);
                        startActivity(intent);

                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(LoginVerification.this)
                                        .setMessage("emaild id doesn't match, Please Register..")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        }).show();


                            }
                        });
                        edtVerifyEmail.setError("emaild id doesn't match, Please Register again");

                    }

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

        switch (v.getId()) {
            case R.id.btnLogin:
                userEmailid=edtVerifyEmail.getText().toString().trim();

                validation();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DialogUtils.cancleProgressDialog();
    }

    public void validation() {
        if(!validateEmailid(userEmailid) ){
            edtVerifyEmail.setError("Incorrect Email id");
        } else {
            sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
            edt=sharedPreferences.edit();
            edt.putString("veryfiEmail",userEmailid);
            edt.apply();

            verification(userEmailid,getToken,androidLogin);
        }
    }
    public static boolean validateEmailid(String email){

        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
