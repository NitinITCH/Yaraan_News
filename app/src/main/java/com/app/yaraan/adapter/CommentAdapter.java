package com.app.yaraan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.yaraan.R;
import com.app.yaraan.activities.CircleTransform;
import com.app.yaraan.activities.Constants;
import com.app.yaraan.models.GetComment;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    ArrayList<GetComment>getCommentArrayList=new ArrayList<>();
    Context context;

    public CommentAdapter(Context context,ArrayList<GetComment>getCommentArrayList){

        this.context=context;
        this.getCommentArrayList=getCommentArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String getCommentImage=getCommentArrayList.get(position).getUserImage();
        String imgUrl= Constants.BASE_IMAGE+getCommentImage;
        Glide.with(context).load(imgUrl).into(holder.ic_imagId);
        holder.txtTime.setText(getCommentArrayList.get(position).getTime());
        holder.txtHeadTitle.setText(getCommentArrayList.get(position).getUserName());
        holder.txtSubhead.setText(getCommentArrayList.get(position).getComment());

        Glide.with(context)
                .load(imgUrl) // add your image url
                .transform(new CircleTransform(context)) // applying the image transformer
                .into(holder.ic_imagId);

    }

    @Override
    public int getItemCount() {
        return getCommentArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgClock;
        CircleImageView ic_imagId;
        TextView txtHeadTitle,txtSubhead,txtTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            ic_imagId=itemView.findViewById(R.id.ic_imagId);
            imgClock=(ImageView)itemView.findViewById(R.id.imgClock);
            txtHeadTitle=(TextView)itemView.findViewById(R.id.txtHeadTitle);
            txtSubhead=(TextView)itemView.findViewById(R.id.txtSubhead);
            txtTime=(TextView)itemView.findViewById(R.id.txtTime);
        }
    }
}
