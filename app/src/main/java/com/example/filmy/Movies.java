package com.example.filmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Movies extends AppCompatActivity {
    MovieAdapter adapter;
    RecyclerView recyclerView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        recyclerView= (RecyclerView) findViewById(R.id.movieList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        fetchData();

    }
    void fetchData(){
        String url="https://api.themoviedb.org/3/search/movie?api_key=7e1dc6437e8262cb626919df301e13a1&query="+name+"&language=en-US";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray newJsonArray = response.getJSONArray("results");
                            ArrayList<MoviesDes> newArray = new ArrayList<>();
                            for (int i = 0; i < newJsonArray.length(); i++) {
                                JSONObject newJsonObject = newJsonArray.getJSONObject(i);
                                String title="NA";
                                String release_date="NA";
                                String rating="NA";
                                String poster="NA";
                                String popularity="NA";
                                String overview="NA";
                                int id=0;
                                if(newJsonObject.has("title")){
                                    title=newJsonObject.getString("title");
                                }
                                if(newJsonObject.has("release_date")){
                                    release_date=newJsonObject.getString("release_date");
                                }
                                if(newJsonObject.has("vote_average")){
                                    double vote_average=newJsonObject.getDouble("vote_average");
                                    rating=Double.toString(vote_average);
                                }
                                if(newJsonObject.has("poster_path")){
                                    String poster_path=newJsonObject.getString("poster_path");
                                    poster="https://www.themoviedb.org/t/p/original"+poster_path;
                                }
                                if(newJsonObject.has("popularity")){
                                    int popu=newJsonObject.getInt("popularity");
                                    popularity=Integer.toString(popu);
                                }
                                if(newJsonObject.has("overview")){
                                    overview=newJsonObject.getString("overview");
                                }
                                if(newJsonObject.has("id")){
                                    id=newJsonObject.getInt("id");
                                }

                               // Log.d("API VALUE",title+"--"+release_date+"--"+rating+"--"+poster+"--"+popularity+"-"+overview+"ID=="+id);
                                MoviesDes movies = new MoviesDes(title, release_date, rating, poster, popularity, overview,id);

                                newArray.add(movies);
                               adapter=new MovieAdapter(Movies.this,newArray);
                               recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException jsonException) {
                            Toast.makeText(Movies.this, "MOVIE NOT AVAILABLE" , Toast.LENGTH_LONG).show();
                            jsonException.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {Toast.makeText(Movies.this, "Something is wrong" , Toast.LENGTH_LONG).show();
                    }
                });
        MySingleton.getInstance(Movies.this).addToRequestQueue(jsonObjectRequest);
    }
}