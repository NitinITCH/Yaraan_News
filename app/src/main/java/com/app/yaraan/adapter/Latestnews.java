package com.app.yaraan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DetailAct;
import com.app.yaraan.R;

import com.app.yaraan.models.HomeNews;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by root on 5/4/18.
 */

public class Latestnews extends RecyclerView.Adapter<Latestnews.MyViewHolder> {

    Context context;
    ArrayList<HomeNews>homeNews=new ArrayList<>();
    String getdetid;

    public Latestnews(Context context,ArrayList<HomeNews>homeNews){
        this.context=context;
        this.homeNews=homeNews;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homegetnes,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String image_string = homeNews.get(position).getImage();
        String authorName = homeNews.get(position).getAuthorName();
        String authorImage = homeNews.get(position).getAuthorImage();
        String authorImg = Constants.BASE_IMAGE + authorImage;
        String image_full_url = Constants.BASE_IMAGE + image_string;

        Glide.with(context).load(image_full_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgineer);
        Glide.with(context).load(authorImg).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgAuthorImg);

        holder.txtDetail.setText(homeNews.get(position).getTitle());
        holder.txtClock.setText(homeNews.get(position).getTime());
        holder.txtAuthorName.setText(authorName);


    }
    @Override
    public int getItemCount() {
        return homeNews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDetail,txtClock,txtAuthorName;
        ImageView homeImage,imgineer,imgClock,imgAuthorImg;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtDetail=(TextView)itemView.findViewById(R.id.txtDetail);
            homeImage=(ImageView)itemView.findViewById(R.id.homeImage);
            imgineer=(ImageView)itemView.findViewById(R.id.imgineer);
            txtClock=(TextView)itemView.findViewById(R.id.txtClock);
            txtAuthorName=(TextView)itemView.findViewById(R.id.txtAuthorName);
            imgAuthorImg=(ImageView)itemView.findViewById(R.id.imgAuthorImg);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getdetid=homeNews.get(getLayoutPosition()).getMasterId();
//                    DetailActFrag detailActFrag=new DetailActFrag();
//                    Bundle bundle=new Bundle();
//                    bundle.putString("detailId",getdetid);
//                    detailActFrag.setArguments(bundle);
//                    ((LandingAct)context).replaceFragment(detailActFrag);
                    Intent intent=new Intent(context, DetailAct.class);
                    intent.putExtra("detailId",getdetid);
                    context.startActivity(intent);
                }
            });
        }
    }
}
