package com.app.yaraan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.app.yaraan.R;
import com.app.yaraan.models.ProfileData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProfileAdapter extends BaseAdapter {

    private ArrayList<ProfileData> images;
    private ArrayList<ProfileData>profileDataArrayList=new ArrayList<>();
    private Context context;
    LayoutInflater inflater = null;
    private PostListener listener;

    public interface PostListener {

        void onPostClick(int position);
    }

    public ProfileAdapter(ArrayList<ProfileData> images, Context context, PostListener listener) {
        this.images = images;
        this.context = context;
        this.listener = listener;

        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        View rootView = inflater.inflate(R.layout.profile_grid_item, null);

        holder.iv_image = rootView.findViewById(R.id.iv_grid_image);


        Glide.with(context)
                .load(images.get(position).getImage())
                .into(holder.iv_image);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPostClick(position);
            }
        });


        return rootView;
    }

    class ViewHolder {

        ImageView iv_image;
    }
}
