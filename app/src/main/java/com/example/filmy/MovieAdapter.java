package com.example.filmy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    ArrayList<MoviesDes> movies;
    Context context;

    public MovieAdapter( Context context, ArrayList<MoviesDes> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.movielist,parent,false);
        return new MovieAdapter.MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MoviesDes movie =movies.get(position);
        holder.title.setText(movie.title);
        holder.rating.setText("Rating: "+movie.rating);
        holder.date.setText("Released On: "+movie.date);
        holder.popularity.setText("Popularity: "+movie.popularity+"%");
        if(!movie.overview.isEmpty())
        holder.overview.setText(movie.overview);
        else
            holder.overview.setText("OverView Not Available");

            Glide.with(holder.itemView.getContext()).load(movie.image).placeholder(R.drawable.logoo).into(holder.poster);
        holder.watchNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= movie.id;
                Intent intent=new Intent(v.getContext(),WatchNow.class);
                intent.putExtra("id",id);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return movies!=null ?movies.size():0;
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,rating,popularity,overview;
        ImageView poster;
        Button watchNow;
        public MovieViewHolder(View itemView) {
            super(itemView);
            this.title=itemView.findViewById(R.id.title);
            this.date=itemView.findViewById(R.id.releaseDate);
            this.rating=itemView.findViewById(R.id.rating);
            this.popularity=itemView.findViewById(R.id.popularity);
            this.overview=itemView.findViewById(R.id.overview);
            this.poster=itemView.findViewById(R.id.poster);
            this.watchNow=itemView.findViewById(R.id.watchNow);
        }
    }
}
