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


public class WatchNow extends AppCompatActivity {
    RecyclerView recyclerView;
    WatchNowAdapter adapter;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_now);
        Intent intent=getIntent();
        id=intent.getIntExtra("id",0);
      recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }
    public void loadData(){
        String url="https://api.themoviedb.org/3/movie/"+id+"/watch/providers?watch_region=IN&api_key=7e1dc6437e8262cb626919df301e13a1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject=response.getJSONObject("results");
                            JSONObject in=jsonObject.getJSONObject("IN");
                            JSONArray flatrate=in.getJSONArray("flatrate");
                            ArrayList<WatchProvider> newArray = new ArrayList<>();
                            for(int i=0;i<flatrate.length();i++){
                                String name="NA";
                                String logo="NA";
                            JSONObject item=flatrate.getJSONObject(i);
                            name=item.optString("provider_name");
                            logo=item.optString("logo_path");
                            WatchProvider provider = new WatchProvider(name,logo);
                                newArray.add(provider);
                                adapter=new WatchNowAdapter(WatchNow.this,newArray);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException jsonException) {
                            Toast.makeText(WatchNow.this, "NOT AVAILABLE" , Toast.LENGTH_LONG).show();
                            jsonException.printStackTrace();
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {Toast.makeText(WatchNow.this, "Something is wrong" , Toast.LENGTH_LONG).show();
                    }
                });
        MySingleton.getInstance(WatchNow.this).addToRequestQueue(jsonObjectRequest);
    }

}