package com.app.yaraan.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.yaraan.R;
import com.app.yaraan.adapter.CommentAdapter;
import com.app.yaraan.models.GetComment;
import com.app.yaraan.retrofit.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment extends AppCompatActivity {

    RecyclerView recyclerComment;
    private EditText edt;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private ImageView sendImage,imgClosee;
    private String newsId;
    private String getedt;
    private String userId;
    Bundle bundle=new Bundle();
    ArrayList<GetComment>getCommentArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        newsId=getIntent().getStringExtra("news_id");
        imgClosee=(ImageView)findViewById(R.id.imgClosee);
        imgClosee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedPreferences=getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("userid","");
        edt=findViewById(R.id.edt);
        recyclerComment=(RecyclerView)findViewById(R.id.recyclerComment);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        recyclerComment.setLayoutManager(llm);
        getedt=edt.getText().toString();
        sendImage=(ImageView)findViewById(R.id.sendImage);


        sendImage.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  getedt=edt.getText().toString();
                  if (getedt!= null) {

                      sendComment(userId,newsId,getedt);
                  }else {
                      DialogMessage.showMessageDialog(Comment.this, "Pleas Commnet");
                  }
              }
          });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getCommentId(newsId);
    }

    void sendComment(String userId, String commentBy, final String comment){
        DialogUtils.showProgressDialog(this,"Pleas Wait");
        RestClient.getServices().getComments(userId,commentBy,comment).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);
                    int succ=jsonObject.getInt("success");
                    if(succ==1){
                        edt.setText(" ");
                        getCommentId(newsId);
                        Toast.makeText(Comment.this,"successfylu send",Toast.LENGTH_SHORT).show();

                    }else {
                        DialogUtils.cancleProgressDialog();
                        String mesg=jsonObject.getString("message");

                        DialogMessage.showMessageDialog(Comment.this,mesg);
                    }

                    DialogUtils.cancleProgressDialog();
                 } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            Toast.makeText(Comment.this,"Internet not working",Toast.LENGTH_SHORT).show();
            }
        });

    }
    void getCommentId(String newsId){

        RestClient.getServices().getCommentList(newsId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);

                    for(int i=0;i<jsonObject.length()-1;i++){
                        JSONObject jsonObject1=jsonObject.getJSONObject(String.valueOf(i));
                        GetComment getComment=new GetComment();
                        getComment.setComment(jsonObject1.getString("comment"));
                        getComment.setCommentId(jsonObject1.getString("comment_id"));
                        getComment.setUserName(jsonObject1.getString("user_name"));
                        getComment.setTime(jsonObject1.getString("time"));
                        getComment.setUserImage(jsonObject1.getString("user_image"));
                        getCommentArrayList.add(getComment);
                    }

                    CommentAdapter commentAdapter=new CommentAdapter(getApplicationContext(),getCommentArrayList);
                    recyclerComment.setAdapter(commentAdapter);
                    DialogUtils.cancleProgressDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Comment.this,"Internet not working",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

