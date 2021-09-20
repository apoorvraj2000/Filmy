package com.example.filmy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyViewHolder> {
    ArrayList<Trending> items;
    Context context;
    public TrendingAdapter(Context context, ArrayList<Trending> items)
    {
        this.context=context;
        this.items=items;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.trending,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Trending item =items.get(position);
        holder.title.setText(item.title);
        holder.rating.setText(item.rating);
        holder.date.setText(item.date);
        Glide.with(holder.itemView.getContext()).load(item.image).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return items!=null ?items.size():0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,rating;
        ImageView poster;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.title=itemView.findViewById(R.id.titleTrend);
            this.date=itemView.findViewById(R.id.releaseTrend);
            this.rating=itemView.findViewById(R.id.ratingTrend);
            this.poster=itemView.findViewById(R.id.imageTrend);
        }
    }
}