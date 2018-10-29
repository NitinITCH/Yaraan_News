package com.app.yaraan.activities;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtils {

    private static ProgressDialog dialog;
    public static void showProgressDialog(Context context,String message){

        dialog = new ProgressDialog(context); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
     }

     public static void cancleProgressDialog(){
        if(dialog!= null){
            dialog.dismiss();
        }
    }

}
