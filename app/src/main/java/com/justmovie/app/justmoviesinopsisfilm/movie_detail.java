package com.justmovie.app.justmoviesinopsisfilm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
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

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class movie_detail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    //deklarasi Interstitial ads
    private InterstitialAd mInterstitialAd;
    private boolean loadingIklan=true;
    private Integer hitung=0;


        public TextView judul, sinopsis, genre, sutradara, bintang_film, produksi, tgl_rilis, negara, durasi;
        public ImageView foto;

        public String kunci_kode_movie, trailer;

        private RequestQueue requestQueue;
        private StringRequest stringRequest;

        ArrayList<HashMap<String, String>> list_data;

        private static final int REQUEST_NUMBER = 999;
        private YouTubePlayerView youTubePlayerView;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_movie_detail);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadInterstitial();
                    finish();
                }
            });

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            judul = (TextView) findViewById(R.id.txtJudul);
            sinopsis = (TextView) findViewById(R.id.txtSinopsis);
            genre = (TextView) findViewById(R.id.txtGenre);
            sutradara = (TextView) findViewById(R.id.txtSutradara);
            bintang_film = (TextView) findViewById(R.id.txtBintangFilm);
            produksi = (TextView) findViewById(R.id.txtProduksi);
            tgl_rilis = (TextView) findViewById(R.id.txtTanggalRilis);
            negara = (TextView) findViewById(R.id.txtNegara);
            durasi = (TextView) findViewById(R.id.txtDurasi);
            foto = (ImageView) findViewById(R.id.imgMovie);

            youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);

            Intent intent = getIntent();
            kunci_kode_movie = intent.getStringExtra(Konfigurasi.KUNCI_KODE_MOVIE);
            trailer = intent.getStringExtra(Konfigurasi.KUNCI_TRAILER);

            list_data = new ArrayList<HashMap<String, String>>();

            getData();

            youTubePlayerView.initialize(Konfigurasi.YOUTUBE_API_KEY, movie_detail.this);
        }

    private void getData(){


        String url2 = Konfigurasi.URL_MOVIE_DETAIL+kunci_kode_movie;
        stringRequest = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response: ", response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONArray jArr = jObj.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

                    JSONObject json = jArr.getJSONObject(0);
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put(Konfigurasi.TAG_KODE_MOVIE, json.getString("kode_movie"));
                    params.put(Konfigurasi.TAG_JUDUL, json.getString("judul"));
                    params.put(Konfigurasi.TAG_SINOPSIS, json.getString("sinopsis"));
                    params.put(Konfigurasi.TAG_GENRE, json.getString("genre"));
                    params.put(Konfigurasi.TAG_SUTRADARA, json.getString("sutradara"));
                    params.put(Konfigurasi.TAG_BINTANG_FILM, json.getString("bintang_film"));
                    params.put(Konfigurasi.TAG_PRODUKSI, json.getString("produksi"));
                    params.put(Konfigurasi.TAG_TGL_RILIS, json.getString("tgl_rilis"));
                    params.put(Konfigurasi.TAG_NEGARA, json.getString("negara"));
                    params.put(Konfigurasi.TAG_DURASI, json.getString("durasi"));
                    params.put(Konfigurasi.TAG_FOTO, json.getString("foto"));
                    params.put(Konfigurasi.TAG_TRAILER, json.getString("trailer"));
                    list_data.add(params);

                    judul.setText(list_data.get(0).get("judul"));
                    sinopsis.setText(list_data.get(0).get("sinopsis"));
                    genre.setText(list_data.get(0).get("genre"));
                    sutradara.setText(list_data.get(0).get("sutradara"));
                    bintang_film.setText(list_data.get(0).get("bintang_film"));
                    produksi.setText(list_data.get(0).get("produksi"));
                    tgl_rilis.setText(list_data.get(0).get("tgl_rilis"));
                    negara.setText(list_data.get(0).get("negara"));
                    durasi.setText(list_data.get(0).get("durasi"));

                    Glide.with(getApplicationContext())
                            .load(list_data.get(0).get("foto"))
                            .transition(withCrossFade())
                            .apply(centerCropTransform()
                                    .priority(Priority.HIGH)
                                    .fitCenter()
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                            )
                            .into(foto);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(movie_detail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        if(!b){

            youTubePlayer.cueVideo(trailer);

            /**
             * there are 2 method you can user here :
             * .cueVideo(), for didn't play automatically
             * .loadVideo(), for do play automatically
             *
             * if you are using play automatically, it better if you hide the video controllers
             * do like below :
             youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);//hide the players controllers
             */
            //youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, REQUEST_NUMBER).show();
        }else{
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)", youTubeInitializationResult.toString()
            );
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_NUMBER){
            youTubePlayerView.initialize(Konfigurasi.YOUTUBE_API_KEY,this);
        }
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
        if (hitung%2==0){
            if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                loadingIklan=true;
            }
        }
    }

}
