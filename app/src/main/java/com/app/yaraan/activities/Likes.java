package com.app.yaraan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.app.yaraan.R;

public class Likes extends AppCompatActivity {
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        img1=(ImageView)findViewById(R.id.img);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Likes.this, SettingPage.class);
                startActivity(intent);
            }
        });
    }
}
