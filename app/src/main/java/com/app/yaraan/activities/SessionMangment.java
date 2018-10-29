package com.app.yaraan.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by root on 8/5/18.
 */

public class SessionMangment {

    Constants constants;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    String userid;
    String key=constants.KEY;
    Context context;

    public static final String IS_LOGGEDIN="isLogin";
    public static final String USERNAME="isLogin";
    public static final String EMAIL="email";
    public static final String FACEBOOK="facebook";
    public static final String PROFILE="profile";
    public static final String USERID="userid";

    public SessionMangment (Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(Constants.KEY,Context.MODE_PRIVATE);
        edt=sharedPreferences.edit();

    }
    public void sesionManagment(String username,String userid,String userEmail,String profile) {
        edt.putBoolean(IS_LOGGEDIN,true);
        edt.putString(EMAIL,userEmail);
        edt.putString(USERID,userid);
        edt.putString(PROFILE,profile);
        edt.putString(USERNAME,username);


    }

    public HashMap<String,String>getUserData(){

        HashMap<String,String>user=new HashMap<>();
        user.put(USERNAME,sharedPreferences.getString(USERNAME,""));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,""));
        user.put(PROFILE,sharedPreferences.getString(PROFILE,""));
        user.put(USERID,sharedPreferences.getString(USERID,""));
        return user;
    }

    public void checkLogin(){
        if(!this.isLogged()){
            Intent intent=new Intent(context,LandingAct.class);
            context.startActivity(intent);
        }
    }

    public boolean isLogged(){
        return sharedPreferences.getBoolean(IS_LOGGEDIN,false);
    }
}
