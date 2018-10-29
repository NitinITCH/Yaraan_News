//package com.app.yaraan.fragments;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.app.yaraan.activities.Constants;
//import com.app.yaraan.activities.DetailAct;
//import com.app.yaraan.activities.DialogUtils;
//import com.app.yaraan.activities.LandingAct;
//import com.app.yaraan.R;
//import com.app.yaraan.adapter.NewsDetail;
//import com.app.yaraan.models.GetDetail;
//import com.app.yaraan.retrofit.RestClient;
//import com.google.firebase.iid.FirebaseInstanceId;
//
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static android.content.Context.MODE_PRIVATE;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class DetailActFrag extends Fragment {
//
//
//
//    String detailid;
//    String masterId;
//    String likestatus;
//    RecyclerView recyclerView;
//    ArrayList<GetDetail> getDetailList = new ArrayList<>();
//    NewsDetail newsDetail;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor edt;
//    public String TAG = DetailAct.class.getName();
//    FrameLayout frameLayout;
//    Bundle bundle = new Bundle();
//    private ImageView imgClose, ic_detail_share, commentImg, ic_heart;
//    private TextView txtHeadLike, txtComment, txtShare;
//    CollapsingToolbarLayout collapsingToolbarLayout;
//    String EXTRA_IMAGE;
//    private ImageView ic_detail_like;
//    Context context;
//    String tokenId = FirebaseInstanceId.getInstance().getToken();
//    LandingAct landingAct = new LandingAct();
//    String newsid;
//    String uerid;
//    String statusImg;
//    String newsIdfirst;
//    String likeNewsid;
//    String facebookId;
//    String liked="0";
//    String like="1";
//    String unlike="0";
//
//    int position=0;
//
//
//
//
//    public DetailActFrag() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        bundle=getArguments();
//        if(bundle!=null){
//            detailid=bundle.getString("detailId");
//            liked=bundle.getString("liked");
//        }
//
//
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail_act, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        intit();
//
//    }
//
//
//    public void intit(){
//
//        ic_heart =getActivity().findViewById(R.id.ic_heart);
//
//        final DetailAct detailAct = (DetailAct) context;
//
//
//
//
//        sharedPreferences=getActivity().getSharedPreferences(Constants.KEY,MODE_PRIVATE);
//        uerid= sharedPreferences.getString("userid","");
//        facebookId=sharedPreferences.getString("faceBook","");
//
//
//
//
//
//        Bundle extras =getActivity().getIntent().getExtras();
//        detailid = extras.getString("detailId");
//        ic_detail_like =getActivity().findViewById(R.id.ic_heart);
//        commentImg =getActivity().findViewById(R.id.imgComment);
//        txtHeadLike =getActivity().findViewById(R.id.txtHeadLike);
//        txtComment =getActivity().findViewById(R.id.txtComment);
//
//
//
//        commentImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Comment frag = new Comment();
////                bundle.putString("news_id", getDetailList.get(0).getMasterId());
////                frag.setArguments(bundle);
////                landingAct.replaceFragment(frag);
//            }
//        });
//
//        ic_detail_share =getActivity().findViewById(R.id.imgShare);
//        ic_detail_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                shareIntent.setType("image/png");
//                shareIntent.putExtra(Intent.EXTRA_TEXT,
//                        "Here is my IMAGE");
//                startActivity(Intent.createChooser(shareIntent, "Share IMAGE Using..."));
//            }
//        });
//
//
//        imgClose =getActivity().findViewById(R.id.imgClose);
//
//        imgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().onBackPressed();
//            }
//        });
//
//
//
//        if(liked.equals("1")){
//            ic_heart.setSelected(true);
//        }else if(liked.equals("0")){
//            ic_heart.setSelected(false);
//        }else if(liked.equals("guest")){
//            ic_heart.setSelected(false);
//        }else {
//            ic_heart.setSelected(false);
//        }
//
//        ic_heart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                statusImg = ic_heart.isSelected()?"1":"0";
//                getLikeDis(uerid,newsid,statusImg,ic_heart,position);
//            }
//        });
//
//        ViewCompat.setTransitionName(getActivity().findViewById(R.id.app_bar_layout), EXTRA_IMAGE);
//        collapsingToolbarLayout = (CollapsingToolbarLayout)getActivity(). findViewById(R.id.collapsing_toolbar);
//        collapsingToolbarLayout.setTitle("Detail");
//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
//        frameLayout = (FrameLayout)getActivity(). findViewById(R.id.frameLayout);
//        recyclerView = (RecyclerView)getActivity().findViewById(R.id.recylerGetDetail);
//        LinearLayoutManager lm = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(lm);
//        sharedPreferences =getActivity().getSharedPreferences(Constants.KEY, MODE_PRIVATE);
////        detailid=sharedPreferences.getString("detail_id","");
//        String liks = sharedPreferences.getString("likes", "");
//        final String comments = sharedPreferences.getString("comment", "");
//        txtHeadLike.setText(liks);
//        txtComment.setText(comments);
//        edt=sharedPreferences.edit();
//
//
//        getDetail(detailid,tokenId);
//        Bundle bundle;
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getDetail(detailid,tokenId);
//    }
//
//    public void getDetail(String id, String token) {
//        DialogUtils.showProgressDialog(getContext(), "Please wait..");
//        RestClient.getServices().getDetail(id, token).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String jsonString = response.body().string();
//
//                    Log.e(TAG, "Jsonstring" + jsonString);
//
//
//                    JSONObject jsonObject = new JSONObject(jsonString);
//
//                    for (int i = 0; i < jsonObject.length() - 1; i++) {
//
//                        JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));
//                        GetDetail getDetail = new GetDetail();
//                        getDetail.setTitle(jsonObject1.getString("title"));
//                        getDetail.setAuthorName(jsonObject1.getString("author"));
//                        getDetail.setAuthorImage(jsonObject1.getString("author_image"));
//                        getDetail.setStatus(jsonObject1.getString("status"));
//                        getDetail.setMasterId(jsonObject1.getString("master_id"));
//
//                        newsid = jsonObject1.getString("master_id");
//
//
//                        newsid = jsonObject1.getString("master_id");
//                        masterId = jsonObject1.getString("master_id");
//                        edt.putString("mid_detailact", masterId);
//                        edt.apply();
//
//
//                        getDetail.setCommentCount(jsonObject1.getString("comment_count"));
//                        getDetail.setTime(jsonObject1.getString("time"));
//                        getDetail.setImage(jsonObject1.getString("image"));
//                        getDetail.setDescription(jsonObject1.getString("description"));
//
//                        getDetailList.add(getDetail);
//
//
//                    }
//
//                    newsDetail = new NewsDetail(getContext(), getDetailList);
//                    recyclerView.setAdapter(newsDetail);
//
//                    DialogUtils.cancleProgressDialog();
//
//                } catch (Exception e) {
//
//                    Log.e(TAG, "exception" + e);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                Log.e(TAG, "throwable" + t);
//            }
//        });
//    }
//
//    public void getDetailList(int pos) {
//
//
//    }
//
//    void getLikeDis(String userid, String likeByus, final String likeStatus, final ImageView imageView, final int pos){
//        DialogUtils.showProgressDialog(getContext(),"Please wait");
//        RestClient.getServices().getLike(userid,likeByus,likeStatus).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String jsonString=response.body().string();
//                    JSONObject jsonObject=new JSONObject(jsonString);
//                    int success=jsonObject.getInt("success");
//                    if(success==1){
//                        if(!TextUtils.isEmpty(uerid) || !TextUtils.isEmpty(facebookId)) {
//                            imageView.setSelected(likeStatus.equals("1"));
//
//
//                        }else {
////                               DialogMessage.showMessageDialog(context,"Register First..");
//                            Toast.makeText(context,"Register First..",Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                    }
//                    DialogUtils.cancleProgressDialog();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(context,"Something Went wromg",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(context,"Internet Not working",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//
//
//
////    public void getLikeStatus(String uId,String nId){
////        RestClient.getServices().getLikeStatus(uId,nId).enqueue(new Callback<ResponseBody>() {
////            @Override
////            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////                try {
////                    String jsonString=response.body().string();
////                    JSONObject jsonObject=new JSONObject(jsonString);
////                    int sucess=jsonObject.getInt("success");
////                    if(sucess==1){
////
////                        likestatus=jsonObject.getString("like_status");
////                        ic_heart.setOnClickListener(new View.OnClickListener() {
////                            @Override
////                            public void onClick(View v) {
////                                if(likestatus.equals("True")){
////                                    ic_heart.setSelected(true);
////                                }else if(likestatus.equals("False")){
////                                    ic_heart.setSelected(false);
////                                }else {
////                                    ic_heart.setSelected(false);
////                                }
////                            }
////                        });
////                    }
////
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////
////            @Override
////            public void onFailure(Call<ResponseBody> call, Throwable t) {
////
////            }
////        });
////    }
//
//
//    void getDisLike(String userid, final String likeByus, final String likeStatus){
//
//    }
//
//
//
//
//    void getLike(String userid, final String likeByus, final String likeStatus){
//        DialogUtils.showProgressDialog(getContext(),"Please wait");
//        RestClient.getServices().getLike(userid,likeByus,likeStatus).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                try {
//
//                    String jsonString=response.body().string();
//                    JSONObject jsonObject=new JSONObject(jsonString);
//                    int success=jsonObject.getInt("success");
//
//
//                    if(success==1){
//                        if(likeStatus.equals("1"))
//                        ic_heart.setSelected(true);
//                    }else {
//                        ic_heart.setSelected(false);
//                    }
//                    DialogUtils.cancleProgressDialog();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(context,"Something Went wromg",Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(context,"Internet Not working",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//
//
//
//}
