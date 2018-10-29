package com.app.yaraan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.yaraan.activities.Constants;
import com.app.yaraan.activities.DetailAct;
import com.app.yaraan.R;
import com.app.yaraan.models.GetFavorite;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 12/4/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {

    ArrayList<GetFavorite>getFavorites=new ArrayList<>();
    Context context;

    public FavoriteAdapter(Context context,ArrayList<GetFavorite>getFavorites){
        this.context=context;
        this.getFavorites=getFavorites;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favoriteadapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String getTitle=getFavorites.get(position).getTitle();


            holder.txtHeading.setText(getFavorites.get(position).getTitle());
            String image_string=getFavorites.get(position).getImage();
            String image_full_url= Constants.BASE_IMAGE+image_string;

            Glide.with(context)
                    .load(image_full_url)
                    .into(holder.imgProfileFavo);

    }

    @Override
    public int getItemCount() {
        return getFavorites.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtHeading;
        CircleImageView imgProfileFavo;

    public MyViewHolder(View itemView) {
        super(itemView);

        txtHeading=(TextView)itemView.findViewById(R.id.txtHeading);
        imgProfileFavo=(CircleImageView)itemView.findViewById(R.id.imgProfileFavo);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=getFavorites.get(getLayoutPosition()).getNewId();
                Intent intent=new Intent(context, DetailAct.class);
                intent.putExtra("detailId",id);
                context.startActivity(intent);
            }
        });
    }


}

}
