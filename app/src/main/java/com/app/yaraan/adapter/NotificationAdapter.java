package com.app.yaraan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DetailAct;
import com.app.yaraan.R;
import com.app.yaraan.models.GetNotification;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by root on 13/4/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

   Context context;
    String notyId;
   ArrayList<GetNotification>getNotificationArrayList=new ArrayList<>();

    public NotificationAdapter(Context context,ArrayList<GetNotification>getNotificationArrayList){
        this.context=context;
        this.getNotificationArrayList=getNotificationArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationadapter,parent,false);

        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String getImage=getNotificationArrayList.get(position).getNotificationsImage();
        String image_full_image= Constants.BASE_IMAGE+getImage;
        Glide.with(context).load(image_full_image).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.headimg);
        holder.txtId.setText(getNotificationArrayList.get(position).getNotificationsMessage());

    }

    @Override
    public int getItemCount() {
        return getNotificationArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView headimg;
        private TextView txtId;
        private LinearLayout linearFirst;
        Bundle bundle=new Bundle();


        public MyViewHolder(View itemView) {
            super(itemView);


            headimg=(ImageView)itemView.findViewById(R.id.headimg);
            txtId=(TextView)itemView.findViewById(R.id.txtId);
            linearFirst=(LinearLayout)itemView.findViewById(R.id.linearFirst);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     notyId=getNotificationArrayList.get(getLayoutPosition()).getNewId();
                    Intent intent=new Intent(context, DetailAct.class);
                    intent.putExtra("detailId",notyId);
                     context.startActivity(intent);

                     //FirstFragment firstFragment=new FirstFragment();

//                   bundle.putString("type",notyId);
//                    firstFragment.setArguments(bundle);
//                    ((LandingAct)context).replaceFragment(firstFragment);
                }
            });

        }
    }
}
