package com.app.yaraan.fragments;


import android.content.ContentResolver;
import android.content.Context;

import static android.provider.Settings.System.AIRPLANE_MODE_ON;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DialogUtils;
import com.app.yaraan.R;
import com.app.yaraan.adapter.NewsDescriptiption;
import com.app.yaraan.models.GetNews;
import com.app.yaraan.retrofit.RecyclerViewClickListener;
import com.app.yaraan.retrofit.RestClient;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements RecyclerViewClickListener {

    RecyclerView recyclerView;
    private WebView webView;
    private boolean loading = true;
    private String poltics = "1";
    private int pos = 0;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ImageView iv_home, iv_categories, iv_search, iv_notification, iv_profile;
    private String type="1", count = "0";
    String getDetailId;
    Bundle bundle;
    int pagnumber=0;
    int pagnumD=0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    ArrayList<GetNews> getNewsList = new ArrayList<>();
    NewsDescriptiption newsDescriptiption;
    public static int PAGINATIONSIZE=5;
    LinearLayoutManager lm;
    boolean isLoading;
    boolean isLastPage;
    View view;
    String typeS;
    TextView txtHeading;
    Toolbar toolbar;
    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(this.view==null) {
            view = inflater.inflate(R.layout.fragment_first, container, false);

            Bundle bundle = getArguments();
            if (bundle != null) {
                type = bundle.getString("type");
            }
            setSelection();
            webView = view.findViewById(R.id.webview);
            recyclerView = view.findViewById(R.id.recyclerFirst);
            lm = new LinearLayoutManager(getContext());


            recyclerView.setLayoutManager(lm);
            recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
            newsDescriptiption = new NewsDescriptiption(getContext(), getNewsList, FirstFragment.this);
            recyclerView.setAdapter(newsDescriptiption);

            sharedPreferences = getContext().getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
            edt = sharedPreferences.edit();
            edt.putString("typeS", type);
            edt.commit();
            edt = sharedPreferences.edit();
            isAirplaneModeOn(getContext());
            typeS = sharedPreferences.getString("typeS", "");
        }
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void onResume() {
        super.onResume();
        Log.e("Resume Called" , "Yes");
        pagnumD=0;
        getNewsList.clear();
        getNews(type,pagnumD);

    }

    private void setSelection(){

        txtHeading=getActivity().findViewById(R.id.txtHeading);
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        txtHeading.setText("برخې");

        iv_home = getActivity().findViewById(R.id.btnHome);
        iv_categories = getActivity().findViewById(R.id.btnCategories);
        iv_search = getActivity().findViewById(R.id.btnSearch);
        iv_notification = getActivity().findViewById(R.id.btnNotifications);
        iv_profile = getActivity().findViewById(R.id.btnProfile);
        iv_home.setImageResource(R.drawable.ic_homeicon);
        iv_categories.setImageResource(R.drawable.ic_categoryicon_sel);
        iv_search.setImageResource(R.drawable.ic_searchicon);
        iv_notification.setImageResource(R.drawable.ic_notification);
        iv_profile.setImageResource(R.drawable.ic_navmenu);
    }

    static boolean isAirplaneModeOn(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        return Settings.System.getInt(contentResolver, AIRPLANE_MODE_ON, 0) != 0;
    }

    public void getNews(String type, final int pagnum) {

        final ArrayList<GetNews>duplicate=new ArrayList<>();


        isLoading=true;
        RestClient.getServices().getnews(type, pagnum).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String jsonString = response.body().string();
                    Log.e("checkResponse", jsonString);
                    JSONObject jsonObject = new JSONObject(jsonString);

                    int success=jsonObject.getInt("success");
                    if(success==1) {

                         isLoading=false;
                        pagnumD++;
                        pagnumber=jsonObject.getInt("count");
                        if(pagnum==pagnumber){
                            isLastPage=true;
                        }
                        for (int i = 0; i < jsonObject.length() - 2; i++) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));
                            GetNews getNews = new GetNews();
                            getNews.setTitle(jsonObject1.getString("title"));
                            getNews.setNewLikes(String.valueOf(jsonObject1.getInt("new_likes")));
                            getNews.setTime(jsonObject1.getString("time"));
                            getNews.setImage(jsonObject1.getString("image"));
                            getNews.setCommentCount(jsonObject1.getString("comment_count"));
                            getNews.setAuthorName(jsonObject1.getString("author_name"));
                            getNews.setAuthorImage(jsonObject1.getString("author_image"));
                            getNews.setGggggggg(jsonObject1.getString("gggggggg"));
                            getNews.setDetailsId(String.valueOf(jsonObject1.getInt("details_id")));
                            getDetailId = String.valueOf(jsonObject1.getInt("details_id"));
                            edt.putString("details_id", getDetailId);
                            edt.apply();
//                        getNewsList.add(getNews);
                            duplicate.add(getNews);
                        }
                    }
                    getNewsList.addAll(duplicate);
                    newsDescriptiption.updatelist(getNewsList);
                    newsDescriptiption.notifyDataSetChanged();
                    isLoading=false;
                    DialogUtils.cancleProgressDialog();

                } catch (Exception e) {
                    isLoading=false;
                    DialogUtils.cancleProgressDialog();
                    Log.e("exception", e.toString());
                    DialogUtils.cancleProgressDialog();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(),"Opps Internet connection issue",Toast.LENGTH_SHORT).show();
                Log.e("throwable", t.toString());
                isLoading=false;
                DialogUtils.cancleProgressDialog();
            }
        });
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();


            if (!isLoading && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGINATIONSIZE) {
                    getNews(type, pagnumD);

                }
            }

        }
        };

    @Override
    public void recyclerViewListClicked(View v, int position) {

    }
}


