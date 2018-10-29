package com.app.yaraan.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yaraan.activities.CircleTransform;
import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DetailAct;
import com.app.yaraan.activities.LandingAct;
import com.app.yaraan.R;
import com.app.yaraan.adapter.ProfileAdapter;
import com.app.yaraan.models.ProfileData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private ImageView iv_home,
            iv_categories,
            iv_search, iv_notification,
            iv_profile,imgSetting;

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_likes;
    private TextView tv_comments;
    private ImageView iv_image;
    private String getId;
    Toolbar toolbar;
    private ArrayList<ProfileData>profileDataArrayList=new ArrayList<>();
    SharedPreferences sharedPreferences;
    private GridView gv_posts;
    private ProfileAdapter adapter;
    private ArrayList<ProfileData> images=new ArrayList<>();
    String userId;
    String facebookId;
    Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences=getContext().getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
        userId=sharedPreferences.getString("userid","");
        facebookId=sharedPreferences.getString("faceBook","");
        setSelections();
        init();
        getProfile(userId);
        getLikes(userId);
    }

    private void init() {


        toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        images = new ArrayList<>();
        imgSetting=getActivity().findViewById(R.id.imgSetting);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LandingAct)getActivity()).replaceFragment(new SettingFirst());
            }
        });
        tv_name = getActivity().findViewById(R.id.tv_profile_name);
        tv_email = getActivity().findViewById(R.id.tv_profile_email);
        tv_likes = getActivity().findViewById(R.id.tv_profile_likes);
        tv_comments = getActivity().findViewById(R.id.tv_profile_comments);
        iv_image = getActivity().findViewById(R.id.iv_profile_image);

//        Picasso.with(getContext())
//                .load("https://graph.facebook.com/" + facebookId+ "/picture?type=large")
//                .into(iv_image);


        gv_posts = getActivity().findViewById(R.id.gv_profile);
        adapter = new ProfileAdapter(images, getActivity(), new ProfileAdapter.PostListener() {
            @Override
            public void onPostClick(int position) {
                Intent intent=new Intent(getContext(), DetailAct.class);
                intent.putExtra("detailId",images.get(position).getDetailsId());
                getActivity().startActivity(intent);

            }
        });

        gv_posts.setAdapter(adapter);
    }

    private void setSelections() {

        iv_home = getActivity().findViewById(R.id.btnHome);
        iv_categories = getActivity().findViewById(R.id.btnCategories);
        iv_search = getActivity().findViewById(R.id.btnSearch);
        iv_notification = getActivity().findViewById(R.id.btnNotifications);
        iv_profile = getActivity().findViewById(R.id.btnProfile);
        iv_image=getActivity().findViewById(R.id.iv_profile_image);
        iv_home.setImageResource(R.drawable.ic_homeicon);
        iv_categories.setImageResource(R.drawable.ic_categoryicon);
        iv_search.setImageResource(R.drawable.ic_searchicon);
        iv_notification.setImageResource(R.drawable.ic_notification);
        iv_profile.setImageResource(R.drawable.ic_notification_sel);

    }
    private void getProfile(final String strUserId) {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, "http://app.hindara.com/api/index.php?action=yarran_user_details", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("success");
                    if(status.equalsIgnoreCase("1")){
                        String strComments = jsonObject.getString("comment_count");
                        tv_comments.setText(strComments);

                        String strNamed = jsonObject.getString("user_name");
                        tv_name.setText(strNamed);
                        String strLikesd = jsonObject.getString("likes_count");
                        tv_likes.setText(strLikesd);
                        String strEmaild = jsonObject.getString("user_email");
                        tv_email.setText(strEmaild);
//                        String strName = jsonObject.getString("user_name");
//                        tv_name.setText(strName);
//                        String strEmail = jsonObject.getString("user_email");
//                        tv_email.setText(strEmail);
//                        String strLikes = jsonObject.getString("likes_count");
//                        tv_likes.setText(strLikes);

                        String strImage=jsonObject.getString("user_image");


                        if(strImage.contains("facebook")){
                            Glide.with(getActivity())
                                .load(strImage) // add your image url
                                .transform(new CircleTransform(getActivity())) // applying the image transformer
                                .into(iv_image);
                        }else {

                            String strImageURL = Constants.BASE_URL+strImage;
                            Glide.with(getActivity())
                                    .load(strImageURL).diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .transform(new CircleTransform(getActivity())) // applying the image transformer
                                    .into(iv_image);
                        }
                         }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", strUserId);
                return params;
            }
        };
        queue.add(request);
    }

    private void getLikes(final String strUserId) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, "http://app.hindara.com/api/index.php?action=yarran_content_like_user", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("respones",response);
                    String status = jsonObject.getString("success");

                    if(status.equalsIgnoreCase("1")){

                        images.size();

                        for(int i =0; i<jsonObject.length()-1; i++){
                             JSONObject jsonObject1=jsonObject.getJSONObject(String.valueOf(i));
                            ProfileData profileData=new ProfileData();
                            profileData.setDetailsId(jsonObject1.getString("details_id"));
                            String strImage = jsonObject1.getString("image");
                            profileData.setImage(Constants.BASE_IMAGE+strImage);

                            images.add(profileData);


                        }
                        Log.e("Size",images.size()+"");
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
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", strUserId);
                return params;
            }
        };

        queue.add(request);

    }
}
