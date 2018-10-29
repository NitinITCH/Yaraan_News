package com.app.yaraan.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.yaraan.activities.DialogUtils;
import com.app.yaraan.R;
import com.app.yaraan.adapter.FavoriteAdapter;
import com.app.yaraan.models.GetFavorite;
import com.app.yaraan.retrofit.RestClient;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFav extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<GetFavorite>getFavorites=new ArrayList<>();

    String token= FirebaseInstanceId.getInstance().getToken();




    public FragmentFav() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavorValues(token);
    }

    public void init(){

        recyclerView=getActivity().findViewById(R.id.recyclerFavorite);
        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

    }

    void getFavorValues(String dToken){

        DialogUtils.showProgressDialog(getContext(),"Pleas wait");

        RestClient.getServices().getFavorValues(dToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String jsonString=response.body().string();
                    JSONObject jsonObject=new JSONObject(jsonString);
                     getFavorites.clear();
                    for(int i=0;i<jsonObject.length()-1;i++) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));

                        String jsonNull = jsonObject1.getString("title");
                        if (!jsonNull.equalsIgnoreCase("null")) {
                            GetFavorite favorite = new GetFavorite();
                            favorite.setImage(jsonObject1.getString("image"));
                            favorite.setNewId(jsonObject1.getString("new_id"));
                            favorite.setTime(jsonObject1.getString("time"));
                            favorite.setTitle(jsonObject1.getString("title"));
                            getFavorites.add(favorite);
                        }
                    }
                    FavoriteAdapter favoriteAdapter=new FavoriteAdapter(getContext(),getFavorites);
                    recyclerView.setAdapter(favoriteAdapter);
                    DialogUtils.cancleProgressDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
