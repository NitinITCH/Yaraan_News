package com.app.yaraan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.yaraan.R;
import com.app.yaraan.models.SearchItem;

import java.util.ArrayList;


public class RecyclerSearchAdapter extends RecyclerView.Adapter<RecyclerSearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchItem> searchItems;
    private SearchListener listener;

    public interface SearchListener {

        void onItemClick(int position);

    }

    public RecyclerSearchAdapter(Context context, ArrayList<SearchItem> searchItems, SearchListener listener) {
        this.context = context;
        this.searchItems = searchItems;
        this.listener = listener;
    }

    @Override
    public RecyclerSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerSearchAdapter.ViewHolder holder, final int position) {

        holder.tv_title.setText(searchItems.get(position).getTitle());

        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;

        public ViewHolder(View itemView) {

            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);

        }
    }
}
