package com.app.yaraan.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by root on 13/4/18.
 */

public class DialogMessage {


    private static AlertDialog.Builder builder;

    public static void showMessageDialog(Context context,String message){

        builder=new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            if(dialog!=null){
                dialog.cancel();
            }
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog!=null) {
                    dialog.cancel();
                }
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
