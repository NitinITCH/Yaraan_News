package com.app.yaraan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yaraan.R;
import com.app.yaraan.activities.Constants;
import com.app.yaraan.adapter.RecyclerSearchAdapter;
import com.app.yaraan.adapter.SearchAdapter;
import com.app.yaraan.models.SearchItem;
import com.app.yaraan.models.SearchNews;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentSearch extends Fragment {

    private ImageView iv_home, iv_categories, iv_search, iv_notification, iv_profile;
    private GridView gv_search;
    private SearchAdapter adapter;
    private RecyclerSearchAdapter searchAdapter;
    private ArrayList<SearchNews> arrayNews;
    private ArrayList<SearchItem> arraySearch;
    String searchid;
    private EditText et_search_bar;
    private RecyclerView rv_search;
    private TextView txtHeading;
    private android.support.v7.widget.Toolbar  toolbar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSelections();
        init();
        getNews();

       et_search_bar.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {


           }
           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

               if(et_search_bar.getText().toString().equalsIgnoreCase("")){

                   rv_search.setVisibility(View.GONE);
                   gv_search.setVisibility(View.VISIBLE);

               }else {

                   getNewsByName(et_search_bar.getText().toString());
                   rv_search.setVisibility(View.VISIBLE);
                   gv_search.setVisibility(View.GONE);
               }
           }
           @Override
           public void afterTextChanged(Editable s) {

           }
       });
    }

    private void init(){

        arrayNews = new ArrayList<>();
        arraySearch = new ArrayList<>();

        et_search_bar = getActivity().findViewById(R.id.et_search_bar);
        rv_search = getActivity().findViewById(R.id.rv_search);
        gv_search = getActivity().findViewById(R.id.gv_search);

        adapter = new SearchAdapter(arrayNews, getActivity(), new SearchAdapter.NewsListener() {
            @Override
            public void onNewsClick(int position) {

                FirstFragment fragment = new FirstFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type",arrayNews.get(position).getMenuId());
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            }
        });

        gv_search.setAdapter(adapter);

        rv_search.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchAdapter = new RecyclerSearchAdapter(getActivity(), arraySearch, new RecyclerSearchAdapter.SearchListener() {

            @Override
            public void onItemClick(int position) {

             searchid=arraySearch.get(position).getId();
                FirstFragment fragment = new FirstFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type",searchid);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
            }
        });

        rv_search.setAdapter(searchAdapter);

    }

    private void setSelections(){

        iv_home = getActivity().findViewById(R.id.btnHome);
        iv_categories = getActivity().findViewById(R.id.btnCategories);
        iv_search = getActivity().findViewById(R.id.btnSearch);
        iv_notification = getActivity().findViewById(R.id.btnNotifications);
        iv_profile = getActivity().findViewById(R.id.btnProfile);

        iv_home.setImageResource(R.drawable.ic_homeicon);
        iv_categories.setImageResource(R.drawable.ic_categoryicon);
        iv_search.setImageResource(R.drawable.ic_searchicon_sel);
        iv_notification.setImageResource(R.drawable.ic_notification);
        iv_profile.setImageResource(R.drawable.ic_navmenu);

        txtHeading=getActivity().findViewById(R.id.txtHeading);
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        txtHeading.setText("لټون");



    }

    private void getNews() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest request = new StringRequest(Request.Method.POST, "http://app.hindara.com/api/index.php?action=yarran_search", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("success");

                    if(status.equalsIgnoreCase("1")){

                        arrayNews.clear();

                        for(int i =0; i<jsonObject.length()-1; i++){

                            SearchNews news = new SearchNews();

                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));
                            String imageURL = Constants.BASE_IMAGE+object.getString("menu_image");

                            news.setImageURL(imageURL);
                            news.setTitle(object.getString("menu_name"));
                            news.setMenuId(object.getString("menu_id"));

                            arrayNews.add(news);
                        }

                        Log.e("Size",arrayNews.size()+"");
                        adapter.notifyDataSetChanged();

                    }else {

                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

    }

    private void getNewsByName(final String strName) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, "http://app.hindara.com/api/index.php?action=search_yaraan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("success");
                    arraySearch.clear();

                    if(status.equalsIgnoreCase("1")){

                        for(int i =0; i<jsonObject.length()-1; i++){

                            SearchItem news = new SearchItem();

                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));
                            news.setId(object.getString("menu_id"));
                            news.setTitle(object.getString("menu_name"));
                            arraySearch.add(news);
                        }

                        Log.e("Size",arraySearch.size()+"");

                    }else {

//                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                    searchAdapter.notifyDataSetChanged();

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", strName);
                return params;
            }
        };
        queue.add(request);
    }

    public void replaceFragment(Fragment fragment){
        String backstatckname=fragment.getClass().getName();

        FragmentManager fm=getActivity().getSupportFragmentManager();
        boolean fragmentPopped=fm.popBackStackImmediate(backstatckname,0);

        FragmentTransaction ft = fm.beginTransaction().addToBackStack(null);
        ft.replace(R.id.allFrag, fragment);
        ft.commit();

    }
}
