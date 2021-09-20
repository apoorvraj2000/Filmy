package com.example.filmy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Search extends AppCompatActivity {
    TrendingAdapter mAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent=getIntent();

        recyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fetchData();
    }
    void fetchData(){
        String url="https://api.themoviedb.org/3/trending/all/day?api_key=7e1dc6437e8262cb626919df301e13a1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray newJsonArray = response.getJSONArray("results");
                            ArrayList<Trending> newArray = new ArrayList<>();
                            for (int i = 0; i < newJsonArray.length(); i++) {
                                JSONObject newJsonObject = newJsonArray.getJSONObject(i);
                                String title="NA";
                                String release_date="NA";
                                String rating="NA";
                                String poster="NA";
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

                                //Log.d("API VALUE",title+"--"+release_date+"--"+rating+"--"+poster);
                                Trending movies = new Trending(title, release_date, rating, poster);

                                newArray.add(movies);
                                mAdapter=new TrendingAdapter(Search.this,newArray);
                                recyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException jsonException) {
                            Toast.makeText(Search.this, "Json exception" , Toast.LENGTH_LONG).show();
                            jsonException.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {Toast.makeText(Search.this, "Something is wrong" , Toast.LENGTH_LONG).show();
                    }
                });
        MySingleton.getInstance(Search.this).addToRequestQueue(jsonObjectRequest);
    }

    public void movie(View view) {
        EditText movie=(EditText)findViewById(R.id.search);
        String name=movie.getText().toString().toLowerCase();
        Intent intent=new Intent(Search.this,Movies.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }
}
