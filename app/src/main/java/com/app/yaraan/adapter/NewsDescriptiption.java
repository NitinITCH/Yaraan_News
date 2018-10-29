package com.app.yaraan.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DetailAct;
import com.app.yaraan.R;

import com.app.yaraan.interfac.GetAllValues;
import com.app.yaraan.models.GetNews;
import com.app.yaraan.retrofit.RecyclerViewClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class NewsDescriptiption extends RecyclerView.Adapter<NewsDescriptiption.MyViewHolder> {

    ArrayList<GetNews>getNewsArrayList;
    Context context;
    String getLike;
    String getComment;
    String title;
    String time;
    String getDetailid=null;
    String like="1";
    String detail_id;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edt;
    RecyclerViewClickListener recyclerViewClickListener;
    ImageLoader imageLoader;

    public NewsDescriptiption(Context context,ArrayList<GetNews>getNewsArrayList,RecyclerViewClickListener recyclerViewClickListener){
        this.recyclerViewClickListener=recyclerViewClickListener;
        this.context=context;
        this.getNewsArrayList=getNewsArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tempx,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        like=getNewsArrayList.get(position).getNewLikes();
        time=getNewsArrayList.get(position).getTime();
        title=getNewsArrayList.get(position).getTitle();
        detail_id=getNewsArrayList.get(position).getDetailsId();
        getComment=getNewsArrayList.get(position).getCommentCount();

//        sharedPreferences=context.getSharedPreferences(Constants.KEY,MODE_PRIVATE);
//        userid=  sharedPreferences.getString("userid","");
////        detail_id=sharedPreferences.getString("details_id","");
//        master_id= sharedPreferences.getString("mid_detailact","");
//
//        sharedPreferences=context.getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
//        edt=sharedPreferences.edit();
//        edt.putString("detail_id",getDetailid);
//        edt.putString("likes",getLike);
//        edt.putString("comment",getComment);
//        edt.putString("time",time);
//        edt.putString("title",title);
//        edt.apply();

        if(position%2==1){
                holder.linearMain.setBackgroundColor(Color.parseColor("#f1f1f1"));
        }else {
             holder.linearMain.setBackgroundColor(Color.parseColor("#ffffff"));

     }


        String image_string=getNewsArrayList.get(position).getImage();
        String imagAuth=getNewsArrayList.get(position).getAuthorImage();
        final String image_full_url=Constants.BASE_IMAGE+image_string;

        String image_auth=Constants.BASE_IMAGE+imagAuth;


        if(position==0){

         holder.llall.setVisibility(View.VISIBLE);
         holder.linearSecond.setVisibility(View.VISIBLE);
        }else {

            holder.llall.setVisibility(View.GONE);
            holder.linearSecond.setVisibility(View.GONE);
        }
                holder.txtnewsLike.setText(like);
                holder.txtnewsLikeHead.setText(like);

                holder.txtHeadMain.setText(title);

                holder.txtClock.setText(time);
                holder.txtDay.setText(time);

                holder.txtTitle.setText(title);

                String getUsid=sharedPreferences.getString("userid","");

                if(!TextUtils.isEmpty(getUsid)) {
                    holder.txtComment.setText(getComment);
                    holder.txtCommentHead.setText(getComment);

                }else {

                   }

                holder.txtAuthor.setText(getNewsArrayList.get(position).getAuthorName());
                holder.txtHeadAuthor.setText(getNewsArrayList.get(position).getAuthorName());

                Glide.with(context).load(image_full_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgSubhead);
                Glide.with(context).load(image_full_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgHead);
                Glide.with(context).load(image_auth).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgAuth);
                Glide.with(context).load(image_auth).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgHeadAuth);

                holder.liked=getNewsArrayList.get(position).getGggggggg();

//        sharedPreferences=context.getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
//        String sharedata=sharedPreferences.getString("statusId","");;

                if(!TextUtils.isEmpty(getNewsArrayList.get(position).getGggggggg()) && getNewsArrayList.get(position).getGggggggg().equalsIgnoreCase("1")){

                    holder.imgLike.setSelected(true);

                }else {

                   holder.imgLike.setSelected(false);
                }



//
//
//                holder.imgLike.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//                        switch (event.getAction()){
//                            case MotionEvent.ACTION_DOWN:{
//                                holder.getLikeDis(userid,detail_id,like);
//                                break;
//                            }
//                            case MotionEvent.ACTION_BUTTON_PRESS:{
//                                holder.getLikeDis(userid,detail_id,dislike);
//                                break;
//                            }
//                        }
//                          return true;
//                    }
//                });

}

    @Override
    public int getItemCount() {
        return getNewsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {




        String liked;
        TextView txtHeadAuthor,txtAuthor,txtCommentHead,txtHeadMain,txtTitle,txtnewsLike,txtComment,txtDay,txtClock,txtnewsLikeHead;
        LinearLayout llall,linearSecond,linearMain,linearLike;
        ImageView imgHeadAuth,imgAuth,imgSubhead,imgHead,imgLike,imgHeart,imgClock,imgChat,imgHeadheart,imgHadclock;
        public MyViewHolder(final View itemView) {
            super(itemView);

//            guestid=sharedPreferences.getString("guestId","");

            txtHeadAuthor=itemView.findViewById(R.id.txtHeadAuthor);
            txtAuthor=itemView.findViewById(R.id.txtAuthor);
            llall= itemView.findViewById(R.id.linearFirst);
            linearLike= itemView.findViewById(R.id.linearLike);
            txtCommentHead= itemView.findViewById(R.id.txtCommentHead);
            txtClock= itemView.findViewById(R.id.txtClock);
            txtnewsLikeHead= itemView.findViewById(R.id.txtnewsLikeHead);
            imgLike= itemView.findViewById(R.id.imgLike);
            txtAuthor=itemView.findViewById(R.id.txtAuthor);
            txtHeadMain= itemView.findViewById(R.id.txtHeadMain);
            linearSecond= itemView.findViewById(R.id.linearSecond);
            linearMain= itemView.findViewById(R.id.linearMain);
            txtnewsLike= itemView.findViewById(R.id.txtnewsLike);
            txtTitle= itemView.findViewById(R.id.txtTitle);
            txtComment= itemView.findViewById(R.id.txtComment);
            txtDay= itemView.findViewById(R.id.txtDay);
            imgHead= itemView.findViewById(R.id.imgHead);
            imgSubhead= itemView.findViewById(R.id.imgSubhead);
            imgAuth=itemView.findViewById(R.id.imgAuth);
            imgHeadAuth=itemView.findViewById(R.id.imgHeadAuth);
            sharedPreferences=context.getSharedPreferences(Constants.KEY, Context.MODE_PRIVATE);
            edt=sharedPreferences.edit();
            sharedPreferences.getString("statusId","");
            edt.putString("detail_id",getDetailid);
            edt.putString("likes",getLike);
            edt.putString("comment",getComment);
            edt.putString("time",time);
            edt.putString("title",title);
            edt.apply();

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    getDetailid=getNewsArrayList.get(getLayoutPosition()).getDetailsId();
//                    DetailActFrag detailActFrag=new DetailActFrag();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("detailId",getDetailid);
//
//                    detailActFrag.setArguments(bundle);
//                    ((LandingAct)context).replaceFragment(new DetailActFrag());

                    Intent intent=new Intent(context, DetailAct.class);
                    intent.putExtra("detailId",getDetailid);
                    intent.putExtra("liked",liked);
                    context.startActivity(intent);
                }
            });

        }

//        void getLikeDis(String userid, final String likeByus, final String likeStatus, final ImageView imageView, final int pos){
//
//            RestClient.getServices().getLike(userid,likeByus,likeStatus).enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                    try {
//
//                        String jsonString=response.body().string();
//                        JSONObject jsonObject=new JSONObject(jsonString);
//                        int success=jsonObject.getInt("success");
//
//                        if(success==1){
//
//                           imageView.setSelected(likeStatus.equals("1"));
//                           getNewsArrayList.get(pos).setGggggggg(likeByus);
//                           notifyDataSetChanged();
//
//                            }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//                }
//            });
//        }
    }
    public  void updatelist(ArrayList<GetNews>arrayList){
       this.getNewsArrayList=arrayList;
       notifyDataSetChanged();
    }
}
