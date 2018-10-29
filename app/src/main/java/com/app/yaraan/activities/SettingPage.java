package com.app.yaraan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.yaraan.R;

public class SettingPage extends AppCompatActivity {

    String userid;
    String facebookId;
    String useremail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    TextView logOut;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        btn=(Button)findViewById(R.id.btn);
        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        userid=sharedPreferences.getString("userid","");
        facebookId=sharedPreferences.getString("facebookid","");

        if(!TextUtils.isEmpty(userid) || !TextUtils.isEmpty(facebookId))
        {
            btn.setText("Logout");
        }else {
            btn.setText("Signin");
        }

        if(btn.getText().equals("Logout")){
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(SettingPage.this,MainPage.class);
                    startActivity(intent);
                    edt=sharedPreferences.edit();
                    edt.clear();
                    edt.apply();
                    finish();
                }
            });
        }else if(btn.getText().equals("Signin")){


        }
    }
}
