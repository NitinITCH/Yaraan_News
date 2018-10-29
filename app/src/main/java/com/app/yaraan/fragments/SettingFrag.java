package com.app.yaraan.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.LandingAct;
import com.app.yaraan.R;
import com.app.yaraan.activities.MainPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFrag extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    private TextView logOut,tx_fav,tx_log,tx_signup;
    private ImageView imgCloseS,imgPrvArrow;
    private LinearLayout lnrPrivacy,lnrAboutus,lnrTerm;
    private Switch switch_nofification;
    private Switch switch_offline;


    public SettingFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_setting, container, false);
        // Inflate the layout for this fragment

        imgCloseS=(ImageView)view.findViewById(R.id.imgCloseS);
        lnrPrivacy=(LinearLayout)view.findViewById(R.id.lnrPrivacy);
        lnrAboutus=(LinearLayout)view.findViewById(R.id.lnrAboutus);
        lnrTerm=(LinearLayout)view.findViewById(R.id.lnrTerm);

        tx_signup=(TextView)view.findViewById(R.id.tx_signup);
        tx_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MainPage.class));
                getActivity().finish();
            }
        });
        sharedPreferences=getActivity().getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
        tx_log=(TextView)view.findViewById(R.id.tx_log);
        tx_log.setOnClickListener(new View.OnClickListener() {
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

        tx_fav=(TextView)view.findViewById(R.id.tx_fav);
        tx_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingAct)getActivity()).replaceFragment(new FragmentFav());
            }
        });

        lnrTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingAct)getActivity()).replaceFragment(new TermCondition());
            }
        });


        lnrAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingAct)getActivity()).replaceFragment(new AboutUs());
            }
        });


        lnrPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingAct)getActivity()).replaceFragment(new Privacy());
            }
        });

        imgCloseS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

//        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sharedPreferences=getActivity().getSharedPreferences(Constants.KEY,MODE_PRIVATE);
//                ed=sharedPreferences.edit();
//                ed.remove("userid");
//                ed.remove("useremail");
//
//
//            }
//        });
        return view;
    }


}
