package com.app.yaraan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.R;
import com.app.yaraan.retrofit.RestClient;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPage extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String FB_ID = "id";
    private static final String USER_FB_NAME = "name";
    private static final String TAG = MainPage.class.getName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "Path_to_your_server";
    private Uri uri;
    String getToken;
    Boolean CheckEditText;
    TextView txtLogin,loginAsguest;

    String androidLogin="Android";
    ImageView imgMainClose;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;

   static String guestid;
    @BindView(R.id.btnMain)
    Button btnMain;
    @BindView(R.id.login_button)

    LoginButton mLoginButton ;
    private CallbackManager mCallbackManager;
    private String LOG_TAG = "MainPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       getToken= FirebaseInstanceId.getInstance().getToken();
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        edt=sharedPreferences.edit();
        imgMainClose=(ImageView)findViewById(R.id.imgMainClose);
        imgMainClose.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(MainPage.this,LandingAct.class);
                 startActivity(intent);
                 finish();
             }
         });

        ButterKnife.bind(this);
//        loginAsguest=(TextView)findViewById(R.id.loginAsguest);
//        loginAsguest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(loginAsguest.isClickable()) {
//                    sharedPreferences = getSharedPreferences(Constants.KEY, MODE_PRIVATE);
//                    edt = sharedPreferences.edit();
//                    edt.putString("guestId", guestid);
//                    edt.apply();
//                    Intent intent = new Intent(MainPage.this, LandingAct.class);
//                    startActivity(intent);
//                }
//            }
//        });

        txtLogin=(TextView)findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainPage.this,LoginVerification.class);
                startActivity(intent);
                finish();
            }
        });

        mLoginButton.setReadPermissions(Arrays.asList(EMAIL));
//        btnMain=(Button)findViewById(R.id.btnMain);
//        btnMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainPage.this, Comment.class);
//                startActivity(intent);
//            }
//        });
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              getFbInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Log.e(LOG_TAG, "FacebookException: " + e);
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.btnMain})
    void onClick(View view){
        switch (view.getId()){
            case R.id.btnMain:
                Intent intent=new Intent(MainPage.this,SignUp.class);
                Bundle bundle=new Bundle();
                bundle.putString("token",getToken);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
   private void goToNextScreen(){
       Intent intent = new Intent(this, LandingAct.class);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);

   }
    private void getFbInfo() {
        DialogUtils.showProgressDialog(this,"Signing you up...");

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {

                            Log.d(LOG_TAG, "fb json object: " + object);
                            Log.d(LOG_TAG, "fb graph response: " + response);

                            String id = object.getString("id");

                            edt.putString("faceBook",id);
                            edt.commit();
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String name = object.optString("name","");
//                            String gender = object.getString("gender");
//                            String birthday = object.getString("birthday");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=large";
                            String email = " ";
                            if (object.has("email")) {
                                email = object.getString("email");
                            }
                            serverSignUp(name,email,id,image_url,androidLogin,getToken);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            DialogUtils.cancleProgressDialog();
                            Log.d(LOG_TAG, "fb graph response: " + e);
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields","id,first_name,last_name,email,gender,birthday,name"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }
    private void serverSignUp(String userName, String userEmail, String fbId,String fbImage,String loginos,String token) {

       Log.e("Name", userName+"");
       Log.e("Email", userEmail+"");
       Log.e("Image", fbImage+"");
        RestClient.getServices().signUp(userName, userEmail, fbId,fbImage, loginos,token).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    Log.d(TAG, "FB signup on :" + jsonString);


                    if (!TextUtils.isEmpty(jsonString)) {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String success = jsonObject.optString("success","0");
                        if(success.equals("1")){

                            goToNextScreen();
                        }

                    }
                    DialogUtils.cancleProgressDialog();


                } catch (Exception e) {
                    Log.e(TAG, "Sesh event: " + e.toString());
                    DialogUtils.cancleProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "Sesh Event: " + t.toString());
                DialogUtils.cancleProgressDialog();

            }
        });
    }

}
