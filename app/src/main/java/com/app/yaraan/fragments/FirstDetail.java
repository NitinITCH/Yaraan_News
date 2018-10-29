//package com.app.yaraan.fragments;
//
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.app.yaraan.activities.Constants;
//import com.app.yaraan.activities.DetailAct;
//import com.app.yaraan.R;
//import com.app.yaraan.adapter.NewsDetail;
//import com.app.yaraan.models.GetDetail;
//import com.app.yaraan.models.GetNews;
//import com.app.yaraan.retrofit.RestClient;
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
///**
// * A simple {@link Fragment} subclass.
// */
//public class FirstDetail extends Fragment {
//
//String detailid;
//RecyclerView recyclerView;
//ArrayList<GetDetail>getDetailList=new ArrayList<>();
//NewsDetail newsDetail;
//SharedPreferences sharedPreferences;
//    public FirstDetail() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_first_detail, container, false);
//        // Inflate the layout for this fragment
//        recyclerView=(RecyclerView)view.findViewById(R.id.recylerGetDetail);
//        LinearLayoutManager lm=new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(lm);
//
//        sharedPreferences=getContext().getSharedPreferences(Constants.KEY,Context.MODE_PRIVATE);
//        detailid=sharedPreferences.getString("detail_id","");
//
//
//
//        getDetail(detailid);
//
//        return view;
//    }
//
//    public void getDetail(String id){
//
//        RestClient.getServices().getDetail(id).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String jsonString=response.body().string();
//
//                    JSONObject jsonObject=new JSONObject(jsonString);
//
//                    for (int i=0;i<jsonObject.length()-1;i++){
//
//                        JSONObject jsonObject1=jsonObject.getJSONObject(String.valueOf(i));
//
//                        GetDetail getDetail=new GetDetail();
//                        getDetail.setTitle(jsonObject1.getString("title"));
//                        getDetail.setTime(jsonObject1.getString("time"));
//                        getDetail.setImage(jsonObject1.getString("image"));
//                        getDetail.setDescription(jsonObject1.getString("description"));
//                       getDetailList.add(getDetail);
//                    }
//                    newsDetail=new NewsDetail(getContext(),getDetailList);
//                    recyclerView.setAdapter(newsDetail);
//
//
//                }catch (Exception e){
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Intent intent=new Intent(getContext(), DetailAct.class);
//                startActivity(intent);
//
//            }
//        });
//    }
//
//}
