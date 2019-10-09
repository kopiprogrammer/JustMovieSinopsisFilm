package com.justmovie.app.justmoviesinopsisfilm;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.justmovie.app.justmoviesinopsisfilm.adapter.AdapterTampilSemuaGridMovie;
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



/**
 * A simple {@link Fragment} subclass.
 */
public class frag_top_weekly extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    ArrayList<HashMap<String, String>> list_data;

    //deklarasi Interstitial ads
    private InterstitialAd mInterstitialAd;
    private boolean loadingIklan=true;
    private Integer hitung=0;


    public frag_top_weekly() {
        // Required empty public constructor
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_top_weekly, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.rvdatamovie);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swRefresh);

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

        return view;
    }

    public void getData(){
        stringRequest = new StringRequest(Request.Method.GET, Konfigurasi.URL_MOVIE_TOP_RATING, new Response.Listener<String>() {
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
                        AdapterTampilSemuaGridMovie adapter = new AdapterTampilSemuaGridMovie((main_menu) getActivity(), list_data);
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
            mInterstitialAd = new InterstitialAd(getActivity());
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
