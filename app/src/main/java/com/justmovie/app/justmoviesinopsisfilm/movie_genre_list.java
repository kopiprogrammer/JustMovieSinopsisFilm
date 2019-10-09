package com.justmovie.app.justmoviesinopsisfilm;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.justmovie.app.justmoviesinopsisfilm.adapter.AdapterTampilSemuaGridMovie;
import com.justmovie.app.justmoviesinopsisfilm.adapter.AdapterTampilSemuaGridMovieCariGenre;
import com.justmovie.app.justmoviesinopsisfilm.app.AppController;
import com.justmovie.app.justmoviesinopsisfilm.app.Konfigurasi;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class movie_genre_list extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;

    public String genre;

    private InterstitialAd mInterstitialAd;
    private boolean loadingIklan=true;
    private Integer hitung=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_movie_genre_list);

        Intent intent = getIntent();
        genre = intent.getStringExtra(Konfigurasi.KUNCI_GENRE);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.rvdatamovie);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swRefresh);

        list_data = new ArrayList<HashMap<String, String>>();
        getData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        final int size = list_data.size();
                        if (size > 0) {
                            for (int i = 0; i < size; i++) {
                                list_data.remove(0);
                            }
                        }
                        //  Toast.makeText(main_menu.this, "Memperbarui Data", Toast.LENGTH_LONG).show();
                        getData();
                    }
                }, 2000);
            }
        });
    }

    public void getData(){
        stringRequest = new StringRequest(Request.Method.GET, Konfigurasi.URL_MOVIE_GENRE_LIST_CARI+genre, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jArr = jObj.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

                    for (int i = 0; i < jArr.length(); i++) {
                        JSONObject json = jArr.getJSONObject(i);
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put(Konfigurasi.TAG_KODE_MOVIE, json.getString("kode_movie"));
                        params.put(Konfigurasi.TAG_JUDUL, json.getString("judul"));
                        params.put(Konfigurasi.TAG_FOTO, json.getString("foto"));
                        params.put(Konfigurasi.TAG_TAHUN, json.getString("tahun"));
                        params.put(Konfigurasi.TAG_TRAILER, json.getString("trailer"));

                        list_data.add(params);
                        AdapterTampilSemuaGridMovieCariGenre adapter = new AdapterTampilSemuaGridMovieCariGenre(movie_genre_list.this, list_data);
                        recyclerView.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(movie_genre_list.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    //Fungsi Interstitial
    public void loadInterstitial() {
        hitung++;
        if (loadingIklan){
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstisial_1));
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
            loadingIklan=false;
        }
        if (hitung%3==0){
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                loadingIklan=true;
            }
        }
    }
}
