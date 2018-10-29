package com.app.yaraan.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yaraan.R;
import com.app.yaraan.adapter.NewsDetail;
import com.app.yaraan.models.GetDetail;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Recycler extends Fragment {

    RecyclerView recyclerView;

    String newsid;
    String uerid;
    String likeNewsid;
    ArrayList<GetDetail> getDetailList = new ArrayList<>();
    NewsDetail newsDetail;
    String masterId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;


    public Recycler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    void init(){
        recyclerView=getActivity().findViewById(R.id.recylerGetDetail);
        recyclerView = getActivity().findViewById(R.id.recylerGetDetail);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
    }
}
