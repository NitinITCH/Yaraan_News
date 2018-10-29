package com.app.yaraan.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DialogUtils;
import com.app.yaraan.R;
import com.app.yaraan.adapter.NotificationAdapter;
import com.app.yaraan.listners.CountBedges;
import com.app.yaraan.models.GetNotification;
import com.app.yaraan.retrofit.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FragmentNotifications extends Fragment {
    CountBedges countBedges;
    String newNotification;
    int oldNotification;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    int storeNotification;
    private ImageView iv_home, iv_categories, iv_search, iv_notification, iv_profile,headimg;
    TextView txtId;
    LinearLayout linearFirst;
    ArrayList<GetNotification>notificationsList=new ArrayList<>();
    String newsId;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView txtHading;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNotification();
        setSelections();
        getActivity().findViewById(R.id.txtNotification).setVisibility(View.GONE);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void setSelections(){

        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recyclerNotif);
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);


        iv_home = getActivity().findViewById(R.id.btnHome);
        iv_categories = getActivity().findViewById(R.id.btnCategories);
        iv_search = getActivity().findViewById(R.id.btnSearch);
        iv_notification = getActivity().findViewById(R.id.btnNotifications);
        iv_profile = getActivity().findViewById(R.id.btnProfile);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        txtHading=getActivity().findViewById(R.id.txtHeading);
        txtHading.setText("یادونې");

        iv_home.setImageResource(R.drawable.ic_homeicon);
        iv_categories.setImageResource(R.drawable.ic_categoryicon);
        iv_search.setImageResource(R.drawable.ic_searchicon);
        iv_notification.setImageResource(R.drawable.ic_notification_sel);
        iv_profile.setImageResource(R.drawable.ic_navmenu);
        toolbar.setVisibility(View.GONE);



    }

    void getNotification(){

        DialogUtils.showProgressDialog(getContext(),"Please Wait");

        RestClient.getServices().getNotification().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);

                        for(int i=0;i<jsonObject.length()-1;i++){
                            JSONObject jsonObject1=jsonObject.getJSONObject(String.valueOf(i));
                        String notificationId=jsonObject1.getString("notifications_id");

                        GetNotification getNotification=new GetNotification();
                        getNotification.setTime(jsonObject1.getString("time"));
                        getNotification.setNotificationsImage(jsonObject1.getString("notifications_image"));
                        getNotification.setNotificationsMessage(jsonObject1.getString("notifications_message"));
                        getNotification.setNotificationsId(jsonObject1.getString("notifications_id"));
                        getNotification.setNewId(jsonObject1.getString("new_id"));
                        notificationsList.add(getNotification);

                    }

                    NotificationAdapter notificationAdapter=new NotificationAdapter(getContext(),notificationsList);
                    recyclerView.setAdapter(notificationAdapter);
                    int shareList=notificationsList.size();
                    sharedPreferences=getContext().getSharedPreferences(Constants.KEY,MODE_PRIVATE);
                    edt=sharedPreferences.edit();
                    edt.putInt("badgesId",shareList);
                    edt.commit();


                    DialogUtils.cancleProgressDialog();


//                    linearFirst.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent=new Intent(getContext(), DetailAct.class);
//                            intent.putExtra("detailId",newsId);
//                            getActivity().startActivity(intent);
//                        }
//                    });

                }catch
                        (Exception e) {

                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getContext(),"Opps Internet connection issue",Toast.LENGTH_SHORT).show();
            }
        });
    }


}
