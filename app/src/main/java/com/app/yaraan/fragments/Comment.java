//package com.app.yaraan.fragments;
//
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//
//import com.app.yaraan.activities.Constants;
//import com.app.yaraan.R;
//import com.app.yaraan.retrofit.RestClient;
//
//import org.json.JSONObject;
//
//import java.io.IOException;
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
//public class Comment extends Fragment {
//
//    RecyclerView recyclerComment;
//    EditText edt;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//
//
//
//    String newsId;
//    String getedt;
//    String userId;
//   Bundle bundle=new Bundle();
//
//    public Comment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//         bundle=getArguments();
//        newsId=bundle.getString("news_id");
//
//
//
//
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_comment, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        init();
//    }
//
//    public void init(){
//
//        sharedPreferences=getContext().getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
//        userId= sharedPreferences.getString("userid","");
//        edt=getActivity().findViewById(R.id.edt);
//        recyclerComment=(RecyclerView)getActivity().findViewById(R.id.recyclerComment);
//        LinearLayoutManager llm=new LinearLayoutManager(getContext());
//        recyclerComment.setLayoutManager(llm);
//        getedt=edt.getText().toString();
//
//
//        edt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendComment(userId,newsId,getedt);
//            }
//        });
//
//
//    }
//
//    void sendComment(String userId,String commentBy,String comment){
//
//        RestClient.getServices().getComments(userId,commentBy,comment).enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                try {
//                    String jsonString=response.body().string();
//                    JSONObject jsonObject=new JSONObject(jsonString);
//
//                    int succ=jsonObject.getInt("success");
//                    if(succ==1){
//
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//
//    }
//}
