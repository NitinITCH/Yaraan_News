package com.app.yaraan.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.yaraan.R;
import com.app.yaraan.fragments.FirstFragment;

import com.app.yaraan.fragments.FragmentFav;
import com.app.yaraan.fragments.FragmentNotifications;
import com.app.yaraan.fragments.FragmentSearch;
import com.app.yaraan.fragments.HomeFragment;
import com.app.yaraan.fragments.SettingFirst;
import com.app.yaraan.listners.CountBedges;
import com.app.yaraan.models.GetHeader;
import com.app.yaraan.models.GetNotification;
import com.app.yaraan.retrofit.RestClient;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingAct extends AppCompatActivity implements View.OnClickListener {

    private int count = 0;

    FrameLayout allFrag;
    ListView muHome;
    String first_fragment = "First_Fragment";
    String home_fragment = "home_Screen";
    String fav_fragment = "fagmet_fav";
    String search_fragment = "search_fragment";
    String profile_fragment = "profile_fragment";
    String Key = "key";
    Toolbar toolbar;
    ArrayList<GetNotification> notificationsList = new ArrayList<>();
    ArrayList<String> getId = new ArrayList<>();
    Context context;
    ArrayList<String> homeListview = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ActionBarDrawerToggle toggle;
    SharedPreferences sharedPreferences;
    LinearLayout lnrNotification;
    SharedPreferences.Editor edt;
    private ImageView imgMenu, btnHome, btnCategories, btnSearch, btnNotifications, btnProfile, backIcon;
    private TextView txtHeading, txtFavorite;
    private List<ArrayList<String>> fragmentName = new ArrayList<>();
    private TextView txtNotification;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        intiComp();
        getNotification();

        lnrNotification = (LinearLayout) findViewById(R.id.lnrNotification);
        sharedPreferences = getSharedPreferences(Constants.KEY, MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        setSupportActionBar(toolbar);

        intiComp();
        getNotification();
//        getHeader();

        replaceFragment(new HomeFragment());
        muHome = (ListView) findViewById(R.id.muhome);
        txtNotification = (TextView) findViewById(R.id.txtNotification);
        btnHome.setOnClickListener(this);
        btnCategories.setOnClickListener(this);
        btnNotifications.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.isDrawerOpen(GravityCompat.START);

            }
        });


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        txtFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtHeading.setText("تاسو خوښ کړي");
                toolbar.setVisibility(View.VISIBLE);
                replaceFragment(new FragmentFav());
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                count++;
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        getAdd();
        getInterstitialAd();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getHeader();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    public void intiComp() {
        txtHeading = (TextView) findViewById(R.id.txtHeading);
        btnHome = findViewById(R.id.btnHome);
        btnCategories = findViewById(R.id.btnCategories);
        btnSearch = findViewById(R.id.btnSearch);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnProfile = findViewById(R.id.btnProfile);
        allFrag = findViewById(R.id.allFrag);
        txtFavorite = (TextView) findViewById(R.id.txtFavorite);
        backIcon = (ImageView) findViewById(R.id.backIcon);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnHome:
                replaceFragment(new HomeFragment());
                count++;
                if(count%2==0){

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                break;

            case R.id.btnCategories:

                replaceFragment(new FirstFragment());
                count++;
                if(count%2==0){

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                break;
            case R.id.btnSearch:

                replaceFragment(new FragmentSearch());
                count++;
                if(count%2==0){

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                break;

            case R.id.btnNotifications:
                replaceFragment(new FragmentNotifications());
                count++;
                if(count%2==0){

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                break;
            case R.id.btnProfile:
                replaceFragment(new SettingFirst());
                count++;
                if(count%2==0){

                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
                break;
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.allFrag, fragment);
        ft.commit();
    }


    public void replaceFragment(Fragment fragment) {
//        String backstatckname=fragment.getClass().getName();
//        Bundle bundle =   new Bundle();
//        bundle.putString(Key,home_fragment);
        FragmentManager fm = getSupportFragmentManager();
//        HomeFragment homeFragment=(HomeFragment)fm.findFragmentByTag(Key);
//        if(homeFragment==null){
//            homeFragment=new HomeFragment();
//            homeFragment.setArguments(bundle);
//        }

//        boolean fragmentPopped=fm.popBackStackImmediate(backstatckname,0);
//        if (!fragmentPopped) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.allFrag, fragment);
        ft.commit();
//        }

    }

    void getHeader() {
        RestClient.getServices().getHeader().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String jsonString = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonString);

                    for (int i = 0; i < jsonObject.length() - 1; i++) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));
                        GetHeader getHeader = new GetHeader();
//                        getHeader.setMenuName(jsonObject1.getString("menu_name"));
                        String getHed = jsonObject1.getString("menu_name");
                        String getIdl = jsonObject1.getString("menu_id");
                        homeListview.add(getHed);
                        getId.add(getIdl);

                    }


                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.navigationtextsetting, homeListview);
                    arrayAdapter.notifyDataSetChanged();
                    muHome.setAdapter(arrayAdapter);
                    muHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            FirstFragment fragment = new FirstFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("type", getId.get(position));
                            fragment.setArguments(bundle);
                            replaceFragment(fragment);
                            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                            drawer.closeDrawer(GravityCompat.START);
                            count++;
                            if(count%2==0){

                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                }
                            }

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    void getNotification() {

        RestClient.getServices().getNotification().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String jsonString = response.body().string();
                    JSONObject jsonObject = new JSONObject(jsonString);

                    for (int i = 0; i < jsonObject.length() - 1; i++) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject(String.valueOf(i));
                        GetNotification getNotification = new GetNotification();
                        getNotification.setTime(jsonObject1.getString("time"));
                        getNotification.setNotificationsImage(jsonObject1.getString("notifications_image"));
                        getNotification.setNotificationsMessage(jsonObject1.getString("notifications_message"));
                        getNotification.setNotificationsId(jsonObject1.getString("notifications_id"));
                        getNotification.setNewId(jsonObject1.getString("new_id"));
                        notificationsList.add(getNotification);
                    }


                    int sizeList = notificationsList.size();
                    int backnote = sharedPreferences.getInt("badgesId", 0);
                    int result = sizeList - backnote;
                    if (result == 0) {
                        txtNotification.setVisibility(View.GONE);

                    } else {
                        txtNotification.setText(String.valueOf(result));
                    }
                    notificationsList.clear();


                } catch (Exception e) {

                    e.printStackTrace();

                    Toast.makeText(LandingAct.this, "Parsing error", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LandingAct.this, "Internet issue", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAdd() {
        MobileAds.initialize(LandingAct.this, "ca-app-pub-3934986051148024~515468367");
        mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void getInterstitialAd(){

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3934986051148024/8637452478");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

}
