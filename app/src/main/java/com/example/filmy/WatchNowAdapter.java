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

public class WatchNowAdapter extends RecyclerView.Adapter<WatchNowAdapter.MyViewHolder> {
    ArrayList<WatchProvider> providers;
    Context context;

    public WatchNowAdapter( Context context,ArrayList<WatchProvider> providers) {
        this.providers = providers;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.providers,parent,false);
        return new WatchNowAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        WatchProvider provider =providers.get(position);
        holder.providerName.setText(provider.name);
        String logoUrl="https://www.themoviedb.org/t/p/original"+provider.logo;
        Glide.with(holder.itemView.getContext()).load(logoUrl).placeholder(R.drawable.logoo).into(holder.providerLogo);
    }

    @Override
    public int getItemCount() {

        return providers!=null ?providers.size():0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView providerLogo;
        TextView providerName;

        public MyViewHolder(View itemView) {
            super(itemView);
            providerLogo=itemView.findViewById(R.id.providerLogo);
            providerName=itemView.findViewById(R.id.providerName);
        }
    }
}
