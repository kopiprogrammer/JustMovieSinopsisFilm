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
import com.justmovie.app.justmoviesinopsisfilm.R;
import com.justmovie.app.justmoviesinopsisfilm.app.Konfigurasi;
import com.justmovie.app.justmoviesinopsisfilm.main_menu;
import com.justmovie.app.justmoviesinopsisfilm.movie_detail;
import com.justmovie.app.justmoviesinopsisfilm.movie_genre_list;

import java.util.ArrayList;
import java.util.HashMap;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.bumptech.glide.request.RequestOptions.centerCropTransform;


public class AdapterTampilSemuaGridGenre extends RecyclerView.Adapter<AdapterTampilSemuaGridGenre.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;
    public String kunci_kode, genre;


    public AdapterTampilSemuaGridGenre(main_menu tdp, ArrayList<HashMap<String, String>>list_data){
        this.context = tdp;
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_genre, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.txtGenre.setText(list_data.get(position).get("genre") +"  ("+ list_data.get(position).get("jumlah")+")");

        genre = list_data.get(position).get("genre").toString();

        holder.cvData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, movie_genre_list.class);
                intent.putExtra(Konfigurasi.KUNCI_GENRE, list_data.get(position).get("genre"));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtGenre;
        CardView cvData;

        public ViewHolder(View itemView){
            super(itemView);

            txtGenre = (TextView) itemView.findViewById(R.id.txtGenre);
            cvData = (CardView) itemView.findViewById(R.id.cvPilihData);

        }



    }
}
