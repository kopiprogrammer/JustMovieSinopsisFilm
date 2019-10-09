package com.justmovie.app.justmoviesinopsisfilm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justmovie.app.justmoviesinopsisfilm.R;
import com.justmovie.app.justmoviesinopsisfilm.app.Konfigurasi;
import com.justmovie.app.justmoviesinopsisfilm.main_menu;
import com.justmovie.app.justmoviesinopsisfilm.movie_negara_list;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterTampilSemuaGridNegara extends RecyclerView.Adapter<AdapterTampilSemuaGridNegara.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;
    public String kunci_kode, genre;


    public AdapterTampilSemuaGridNegara(main_menu tdp, ArrayList<HashMap<String, String>>list_data){
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

        holder.txtGenre.setText(list_data.get(position).get("negara")+"  ("+list_data.get(position).get("jumlah")+")");

        holder.cvData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, movie_negara_list.class);
                intent.putExtra(Konfigurasi.KUNCI_NEGARA, list_data.get(position).get("negara"));
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
        //ImageView imgFoto;
        CardView cvData;

        public ViewHolder(View itemView){
            super(itemView);

            txtGenre = (TextView) itemView.findViewById(R.id.txtGenre);
          //  imgFoto = (ImageView) itemView.findViewById(R.id.imgMovie);
            cvData = (CardView) itemView.findViewById(R.id.cvPilihData);

        }



    }
}
