package com.app.yaraan.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DialogMessage;
import com.app.yaraan.activities.LandingAct;
import com.app.yaraan.activities.MainPage;
import com.app.yaraan.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFirst extends Fragment {

    private LinearLayout lnrProfile,lnrHelp,lnrSetting;
    private ImageView iv_home, iv_categories, iv_search, iv_notification, iv_profile,imgSetting;
    private Button btnLog;
    private TextView logOut;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    String userid=null;
    String userEmail=null;
    String facebook=null;

    private Toolbar toolbar;
    private TextView txtHading;



    public SettingFirst() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intit();
        setSelections();
    }

    private void intit(){
        lnrProfile=getActivity().findViewById(R.id.lnrprofile);
        btnLog=getActivity().findViewById(R.id.btnLog);
        lnrHelp=getActivity().findViewById(R.id.lnrHelp);
        lnrSetting=getActivity().findViewById(R.id.lnrSetting);

        sharedPreferences=getContext().getSharedPreferences(Constants.KEY,MODE_PRIVATE);
        userid=sharedPreferences.getString("userid","");
        userEmail=sharedPreferences.getString("useremail","");
        facebook=sharedPreferences.getString("faceBook","");


        if(!TextUtils.isEmpty(userid) || !TextUtils.isEmpty(facebook))
        {
            btnLog.setText("Logout");

        }else {
                btnLog.setText("Signin");
            }

        if(btnLog.getText().equals("Logout")){


            lnrProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LandingAct)getActivity()).replaceFragment(new ProfileFragment());
                }
            });



            lnrSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LandingAct)getActivity()).replaceFragment(new SettingFrag());
                }
            });

            btnLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),MainPage.class);
                    startActivity(intent);
                    edt=sharedPreferences.edit();
                    edt.clear();
                    edt.apply();
                    getActivity().finish();
                }
            });


        }else if(btnLog.getText().equals("Signin")){

            btnLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getContext(),MainPage.class);
                    startActivity(intent);
                }
            });


            lnrSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LandingAct)getActivity()).replaceFragment(new SettingFrag());
                }
            });

            lnrProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogMessage.showMessageDialog(getContext(),"لومړی اکاونټ جوړ کړئ");
                }
            });

        }
//        edt=sharedPreferences.edit();
//        edt.clear();
//        edt.apply();
//        getActivity().finish();

        }



    private void setSelections() {

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);

        txtHading=getActivity().findViewById(R.id.txtHeading);
        txtHading.setText("یادونې");//notification

        iv_home = getActivity().findViewById(R.id.btnHome);
        iv_categories = getActivity().findViewById(R.id.btnCategories);
        iv_search = getActivity().findViewById(R.id.btnSearch);
        iv_notification = getActivity().findViewById(R.id.btnNotifications);
        iv_profile = getActivity().findViewById(R.id.btnProfile);

        iv_home.setImageResource(R.drawable.ic_homeicon);
        iv_categories.setImageResource(R.drawable.ic_categoryicon);
        iv_search.setImageResource(R.drawable.ic_searchicon);
        iv_notification.setImageResource(R.drawable.ic_notification);
        iv_profile.setImageResource(R.drawable.ic_navmenu_sel);

    }
}
