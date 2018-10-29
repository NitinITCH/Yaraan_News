package com.app.yaraan.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DetailAct;
import com.app.yaraan.activities.DialogUtils;
import com.app.yaraan.R;
import com.app.yaraan.adapter.Latestnews;
import com.app.yaraan.models.HomeNews;
import com.app.yaraan.retrofit.RestClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    RecyclerView recylerHome;
    private AdView mAdView;
    ImageView homeImage, imgAuthor;
    TextView txtHeadMain, textAuthor, txtClock, txtnewsLikeHead, txtCommentHead;
    ArrayList<HomeNews> homeNewsList = new ArrayList<>();
    Toolbar toolbar;
    TextView txtHeading;
    private ImageView iv_home, iv_categories, iv_search, iv_notification, iv_profile, imgLastClock;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        setSelections();


        homeImage = (ImageView) view.findViewById(R.id.homeImage);
        txtHeadMain = (TextView) view.findViewById(R.id.txtHeadMain);
        imgLastClock = (ImageView) view.findViewById(R.id.imgLastClock);
        txtClock = view.findViewById(R.id.txtClock);

        txtnewsLikeHead = view.findViewById(R.id.txtnewsLikeHead);
        txtCommentHead = view.findViewById(R.id.txtnewsLikeHead);

        txtHeading = getActivity().findViewById(R.id.txtHeading);
        toolbar = getActivity().findViewById(R.id.toolbar);

        toolbar.setVisibility(View.VISIBLE);
        txtHeading.setText("سرپاڼه");

        imgAuthor = (ImageView) view.findViewById(R.id.imgAuthor);
        textAuthor = (TextView) view.findViewById(R.id.textAuthor);

        recylerHome = (RecyclerView) view.findViewById(R.id.recylerHome);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recylerHome.setLayoutManager(llm);
        recylerHome.getLayoutManager().scrollToPosition(1);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//      getAdd();
    }

    public void getAdd(){
        MobileAds.initialize(getContext(), "ca-app-pub-3934986051148024/2279093538");
        mAdView =(AdView)getActivity().findViewById(R.id.adView);
//        mAdView.setAdSize(AdSize.BANNER);

//        mAdView.setAdUnitId(getString(R.string.add_banner_footer));

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    void getLatest() {
        DialogUtils.showProgressDialog(getContext(), "Pleas wait");
        RestClient.getServices().getLatestNews().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String jsonString = response.body().string();

                    Log.e("respones", jsonString);

                    JSONObject jsonObject = new JSONObject(jsonString);

                    Log.e("secondRespos", jsonObject.toString());

                    for (int i = 0; i < jsonObject.length() - 1; i++) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));
                        HomeNews homeNews = new HomeNews();
                        homeNews.setTitle(jsonObject1.getString("title"));
                        homeNews.setImage(jsonObject1.getString("image"));
                        homeNews.setTime(jsonObject1.getString("time"));
                        homeNews.setAuthorName(jsonObject1.getString("author_name"));
                        homeNews.setAuthorImage(jsonObject1.getString("author_image"));
                        homeNews.setMasterId(jsonObject1.getString("master_id"));
                        final String masterid = jsonObject1.getString("master_id");


                        String getAuthorName = jsonObject1.getString("author_name");
                        String getAutho = jsonObject1.getString("author_image");
                        String image_full_url = Constants.BASE_IMAGE + jsonObject1.getString("image");
                        String author_image = Constants.BASE_IMAGE + getAutho;
                        if (i == 0) {
                            String getTitle = jsonObject1.getString("title");
                            txtHeadMain.setText(getTitle);
                            String timeM = jsonObject1.getString("time");
                            txtClock.setText(timeM);
                            String like_count = jsonObject1.getString("like_count");
                            txtnewsLikeHead.setText(like_count);
                            String comments_count = jsonObject1.getString("comments_count");
                            txtCommentHead.setText(comments_count);
                            Glide.with(getContext()).load(author_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgAuthor);
                            textAuthor.setText(getAuthorName);
                            Glide.with(getContext()).load(image_full_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(homeImage);
                            homeImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), DetailAct.class);
                                    intent.putExtra("detailId", masterid);
                                    getActivity().startActivity(intent);
                                }
                            });
                        }

                        homeNewsList.add(homeNews);
                    }
                    Latestnews latestnews = new Latestnews(getContext(), homeNewsList);
                    recylerHome.setAdapter(latestnews);
                    DialogUtils.cancleProgressDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Opps Internet connection issue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLatest();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void setSelections() {
        iv_home = getActivity().findViewById(R.id.btnHome);
        iv_categories = getActivity().findViewById(R.id.btnCategories);
        iv_search = getActivity().findViewById(R.id.btnSearch);
        iv_notification = getActivity().findViewById(R.id.btnNotifications);
        iv_profile = getActivity().findViewById(R.id.btnProfile);

        iv_home.setImageResource(R.drawable.ic_homeicon_sel);
        iv_categories.setImageResource(R.drawable.ic_categoryicon);
        iv_search.setImageResource(R.drawable.ic_searchicon);
        iv_notification.setImageResource(R.drawable.ic_notification);
        iv_profile.setImageResource(R.drawable.ic_navmenu);

    }

}
