package com.app.yaraan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yaraan.R;
import com.app.yaraan.adapter.NewsDescriptiption;
import com.app.yaraan.models.GetNews;
import com.app.yaraan.retrofit.RecyclerViewClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentNewsByType extends Fragment {

    private RecyclerView rv_news;
    private NewsDescriptiption adapter;
    private ArrayList<GetNews> arrayNews;
    private String type, count = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle!=null){

            type = bundle.getString("type");
        }

        return inflater.inflate(R.layout.fragment_news_by_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }

    public void onResume() {

        super.onResume();

        getNews(type, count);

    }


    private void init() {

        arrayNews = new ArrayList<>();

        rv_news = getActivity().findViewById(R.id.rv_news);
        rv_news.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new NewsDescriptiption(getActivity(), arrayNews, new RecyclerViewClickListener() {

            @Override
            public void recyclerViewListClicked(View v, int position) {


            }
        });

        rv_news.setAdapter(adapter);
    }

    private void getNews(final String strType, final String strPageNo) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, "http://allappshere.in/ryder/yaraan/index.php?action=yarran_content", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("success");

                    if (status.equalsIgnoreCase("1")) {

                        arrayNews.clear();


                        for (int i = 0; i < jsonObject.length() - 2; i++) {

                            GetNews news = new GetNews();

                            JSONObject object = jsonObject.getJSONObject(String.valueOf(i));
                            news.setDetailsId(object.getString("details_id"));
                            news.setTitle(object.getString("title"));
                            news.setImage("https://allappshere.in/ryder/yaraan/images/" + object.getString("image"));
                            news.setAuthorName(object.getString("author_name"));
                            news.setTime(object.getString("time"));
                            news.setNewLikes(object.getString("new_likes"));
                            news.setCommentCount(object.getString("comment_count"));
                            news.setGggggggg("guest");
                            arrayNews.add(news);
                        }

                        Log.e("Size", arrayNews.size() + "");
                        rv_news.getRecycledViewPool().clear();
                        adapter.notifyDataSetChanged();

                    } else {

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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();

                params.put("type", strType);
                params.put("page_number", strPageNo);

                return params;
            }
        };

        queue.add(request);

    }
}
