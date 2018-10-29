package com.app.yaraan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.yaraan.R;
import com.app.yaraan.models.SearchNews;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    private ArrayList<SearchNews> arrayNews;
    private Context context;
    LayoutInflater inflater = null;
    private NewsListener listener;
    public interface NewsListener {
        void onNewsClick(int position);
    }
    public SearchAdapter(ArrayList<SearchNews> arrayNews, Context context, NewsListener listener) {
        this.arrayNews = arrayNews;
        this.context = context;
        this.listener = listener;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayNews.size();
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

        View rootView = inflater.inflate(R.layout.search_grid_item, null);
        holder.iv_image = rootView.findViewById(R.id.iv_grid_image);
        holder.tv_title = rootView.findViewById(R.id.tv_title);
        holder.tv_title.setText(arrayNews.get(position).getTitle());

        Glide.with(context)
                .load(arrayNews.get(position).getImageURL())
                .into(holder.iv_image);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onNewsClick(position);
            }
        });

        return rootView;
    }

    class ViewHolder{

        TextView tv_title;
        ImageView iv_image;
    }
}
