package com.app.yaraan.firbasetoken;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.LandingAct;

import com.app.yaraan.R;
import com.app.yaraan.activities.SessionMangment;
import com.app.yaraan.retrofit.RestClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pusher.pushnotifications.PushNotifications;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    public static int splashTimeOut=4000;

    SharedPreferences sharedPreferences;
    boolean hasLogintrue;
    String userid;
    String useremail;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS;
    String facbookId;

    String token=FirebaseInstanceId.getInstance().getToken();
    String type="Android";
    String intId= FirebaseInstanceId.getInstance().getId();
    SessionMangment sessionMangment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_splash_screen);

        tokenType(token,type);

        sessionMangment=new SessionMangment(SplashScreen.this);


        PushNotifications.start(getApplicationContext(), intId);
        PushNotifications.subscribe("hello");

        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        userid=  sharedPreferences.getString("userid","");
        useremail=  sharedPreferences.getString("useremail","");
        facbookId=sharedPreferences.getString("faceBook","");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


//                PERMISSIONS = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS, Manifest.permission.CAMERA};
//                if(!hasPermissions(SplashScreen.this, PERMISSIONS)){
//                    ActivityCompat.requestPermissions(SplashScreen.this, PERMISSIONS, PERMISSION_ALL);
//                }

                if(sessionMangment.isLogged()){

                    Intent intent=new Intent(SplashScreen.this,LandingAct.class);
                    startActivity(intent);
                    finish();

                }else {

                    Intent intent=new Intent(SplashScreen.this, LandingAct.class);
                    startActivity(intent);
                    finish();

                }

//                if(!TextUtils.isEmpty(useremail) || !TextUtils.isEmpty(userid) || !TextUtils.isEmpty(facbookId)){
//
//                }else {
//
//                }



            }
        },splashTimeOut);
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    void tokenType(String token,String type){
        RestClient.getServices().deviceToken(token,type).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
