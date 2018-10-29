package com.app.yaraan.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.R;
import com.app.yaraan.adapter.NewsDetail;

import com.app.yaraan.models.GetDetail;
import com.app.yaraan.retrofit.RestClient;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAct extends AppCompatActivity {

    String detailid;
    String masterId;
    RecyclerView recyclerView;
    ArrayList<GetDetail> getDetailList = new ArrayList<>();
    NewsDetail newsDetail;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    public String TAG = DetailAct.class.getName();
    FrameLayout frameLayout;
    Bundle bundle = new Bundle();
    private ImageView imgClose, ic_detail_share, commentImg, ic_heart;
    private TextView txtHeadLike, txtComment, txtShare;
    CollapsingToolbarLayout collapsingToolbarLayout;
    String EXTRA_IMAGE;
    private ImageView ic_detail_like;
    Context context;
    String tokenId = FirebaseInstanceId.getInstance().getToken();
    LandingAct landingAct = new LandingAct();
    String newsid;
    String imagShare;
    String uerid;
    String facebookId;
    String description;
    String likeNewsid;
    int position=0;
    String statusImg;
    String getLiked;
    String check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ic_heart = (ImageView) findViewById(R.id.ic_heart);
        final DetailAct detailAct = (DetailAct) context;
        sharedPreferences=getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        uerid= sharedPreferences.getString("userid","");
        facebookId=sharedPreferences.getString("faceBook","");

        Bundle extras = getIntent().getExtras();
        detailid = extras.getString("detailId");
        getLiked=extras.getString("liked");
        ic_detail_like = (ImageView) findViewById(R.id.ic_heart);
        commentImg = (ImageView) findViewById(R.id.imgComment);

        if(!TextUtils.isEmpty(uerid)|| !TextUtils.isEmpty(facebookId)) {
          commentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailAct.this, Comment.class);
                    intent.putExtra("news_id", newsid);
                    startActivity(intent);
//                Comment frag = new Comment();
//                bundle.putString("news_id", getDetailList.get(0).getMasterId());
//                frag.setArguments(bundle);
//                landingAct.replaceFragment(frag);
                }
             });
           }
           else {
//          DialogMessage dialogMessage=new DialogMessage();
//          dialogMessage.showMessageDialog(context,"Register First");
      }

        ic_detail_share = (ImageView) findViewById(R.id.imgShare);


        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Detail");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recylerGetDetail);
        LinearLayoutManager lm = new LinearLayoutManager(DetailAct.this);
        recyclerView.setLayoutManager(lm);
        sharedPreferences = getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
//        detailid=sharedPreferences.getString("detail_id","");
        String liks = sharedPreferences.getString("likes", "");
        final String comments = sharedPreferences.getString("comment", "");
        edt=sharedPreferences.edit();

        Bundle bundle;

//        if(getLiked.equals("1")){
//            ic_heart.setSelected(true);
//        }else if(getLiked.equals("0")){
//            ic_heart.setSelected(false);
//        }else if(getLiked.equals("guest")){
//            ic_heart.setSelected(false);
//        }else if(getLiked.equals("null")){
//            ic_heart.setSelected(false);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        ic_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                statusImg = ic_heart.isSelected()?"0":"1";
                check=statusImg;
//                edt.putString("statusId",check);
                edt.commit();
                if(!TextUtils.isEmpty(uerid) || !TextUtils.isEmpty(facebookId)) {
                getLikeDis(uerid,newsid,statusImg,ic_heart,position);
                }else {
                    DialogMessage.showMessageDialog(DetailAct.this,"لومړی اکاونټ جوړ کړئ");
                }
            }
        });


            getDetail(detailid, tokenId);


    }

    public void getDetail(String id, String token) {
        DialogUtils.showProgressDialog(this, "Please wait..");
        RestClient.getServices().getDetail(id, token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    Log.e(TAG, "Jsonstring" + jsonString);

                    JSONObject jsonObject = new JSONObject(jsonString);

                    for (int i = 0; i < jsonObject.length() - 1; i++) {

                        final JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));
                        GetDetail getDetail = new GetDetail();
                        getDetail.setTitle(jsonObject1.getString("title"));
                        getDetail.setAuthorName(jsonObject1.getString("author_name"));
                        getDetail.setAuthorImage(jsonObject1.getString("author_image"));
                        getDetail.setStatus(jsonObject1.getString("status"));
                        getDetail.setMasterId(jsonObject1.getString("master_id"));

                        newsid = jsonObject1.getString("master_id");
                        masterId = jsonObject1.getString("master_id");
                        edt.putString("mid_detailact", masterId);
                        edt.apply();
                        imagShare=jsonObject1.getString("author_image");
                        description=jsonObject1.getString("description");

                         String linkShare=getDetail.getMasterId();
                         final String url= Constants.BASE_URL+"web.php?newId="+linkShare;

                        ic_detail_share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.setType("image/png");
                                shareIntent.putExtra(Intent.EXTRA_TEXT,url);
                                startActivity(Intent.createChooser(shareIntent,imagShare));
                            }
                        });

                        getDetail.setCommentCount(jsonObject1.getString("comment_count"));
                        getDetail.setTime(jsonObject1.getString("time"));
                        getDetail.setImage(jsonObject1.getString("image"));
                        getDetail.setDescription(jsonObject1.getString("description"));
                        getDetailList.add(getDetail);
                    }


//                    if(!TextUtils.isEmpty(uerid) || !TextUtils.isEmpty(facebookId)) {
//                        if (!TextUtils.isEmpty(getDetailList.get(position).getStatus()) && getDetailList.get(position).getStatus().equals("1")) {
//                            ic_heart.setSelected(true);
//
//
//
//                        } else {
//                            ic_heart.setSelected(false);
//
//                        }
//
//                        ic_heart.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                int pos = 0;
//                                likeNewsid = getDetailList.get(pos).getMasterId();
//                                String likestat = ic_heart.isSelected() ? "0" : "1";
//                                getLikeDis(uerid,newsid,likestat,ic_heart,pos);
//                            }
//                        });
//                    }else {
//                        Toast.makeText(DetailAct.this,"Register First..",Toast.LENGTH_SHORT).show();
////                        DialogMessage.showMessageDialog(context,"Register First...");
//                    }
                    newsDetail = new NewsDetail(DetailAct.this, getDetailList);
                    recyclerView.setAdapter(newsDetail);
                    DialogUtils.cancleProgressDialog();

                } catch (Exception e) {
                    Log.e(TAG, "exception" + e);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "throwable" + t);
            }
        });
    }


    void giveLikeStatus(){

    }

    void getLikeDis(String userid, String likeByus, final String likeStatus, final ImageView imageView, final int pos){
        DialogUtils.showProgressDialog(this,"Please wait");
        RestClient.getServices().getLike(userid,likeByus,likeStatus).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);
                    int success=jsonObject.getInt("success");

                    if(success==1){

                        if(!TextUtils.isEmpty(uerid) || !TextUtils.isEmpty(facebookId)) {

                               if(check.equals("1")){

                                   imageView.setSelected(likeStatus.equals("1"));





                               }else  {

                                   imageView.setSelected(false);
                               }


                           }else {
//                               DialogMessage.showMessageDialog(context,"Register First..");
                               DialogMessage.showMessageDialog(DetailAct.this,"لومړی اکاونټ جوړ کړئ");
                           }
                    }else{

                        Toast.makeText(DetailAct.this,"Error..",Toast.LENGTH_SHORT).show();
                    }
                    DialogUtils.cancleProgressDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Something Went wromg",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context,"Internet Not working",Toast.LENGTH_SHORT).show();
            }
        });
    }


}

