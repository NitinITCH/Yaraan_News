package com.app.yaraan.firbasetoken;

import android.content.SharedPreferences;
import android.util.Log;

import com.app.yaraan.activities.Constants;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by root on 23/3/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;

    @Override
    public void onTokenRefresh() {





        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        edt=sharedPreferences.edit();
        edt.putString("deviceToke",refreshedToken);
        edt.apply();

        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }

}
