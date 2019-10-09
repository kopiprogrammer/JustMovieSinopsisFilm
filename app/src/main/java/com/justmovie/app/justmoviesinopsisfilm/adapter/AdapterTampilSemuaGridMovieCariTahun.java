package com.justmovie.app.justmoviesinopsisfilm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.justmovie.app.justmoviesinopsisfilm.R;
import com.justmovie.app.justmoviesinopsisfilm.app.Konfigurasi;
import com.justmovie.app.justmoviesinopsisfilm.main_menu;
import com.justmovie.app.justmoviesinopsisfilm.movie_detail;
import com.justmovie.app.justmoviesinopsisfilm.movie_negara_list;
import com.justmovie.app.justmoviesinopsisfilm.movie_tahun_list;

import java.util.ArrayList;
import java.util.HashMap;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;


public class AdapterTampilSemuaGridMovieCariTahun extends RecyclerView.Adapter<AdapterTampilSemuaGridMovieCariTahun.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;
    public String kunci_kode, trailer;


    public AdapterTampilSemuaGridMovieCariTahun(movie_tahun_list tdp, ArrayList<HashMap<String, String>>list_data){
        this.context = tdp;
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_data, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(list_data.get(position).get("foto"))
                .transition(withCrossFade())
                .apply(centerCropTransform()
                        .priority(Priority.HIGH)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .fitCenter()
                )
                .into(holder.imgFoto);
        holder.txtJudul.setText(list_data.get(position).get("judul"));

        kunci_kode = list_data.get(position).get("kode_movie").toString();
        trailer = list_data.get(position).get("trailer").toString();

        holder.cvData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((movie_tahun_list)context ).loadInterstitial();
                Intent intent = new Intent(context, movie_detail.class);
                intent.putExtra(Konfigurasi.KUNCI_KODE_MOVIE, list_data.get(position).get("kode_movie"));
                intent.putExtra(Konfigurasi.KUNCI_TRAILER, list_data.get(position).get("trailer"));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtJudul;
        ImageView imgFoto;
        CardView cvData;

        public ViewHolder(View itemView){
            super(itemView);

            txtJudul = (TextView) itemView.findViewById(R.id.txtJudul);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgMovie);
            cvData = (CardView) itemView.findViewById(R.id.cvPilihData);

        }



    }
}
