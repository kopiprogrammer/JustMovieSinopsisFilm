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
import com.justmovie.app.justmoviesinopsisfilm.frag_tahun;
import com.justmovie.app.justmoviesinopsisfilm.main_menu;
import com.justmovie.app.justmoviesinopsisfilm.movie_tahun_list;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterTampilSemuaGridTahun extends RecyclerView.Adapter<AdapterTampilSemuaGridTahun.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;
    public String tahun;


    public AdapterTampilSemuaGridTahun(main_menu tdp, ArrayList<HashMap<String, String>>list_data){
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
       /* Glide.with(context)
                .load(list_data.get(position).get("foto"))
                .transition(withCrossFade())
                .apply(centerCropTransform()
                        .priority(Priority.HIGH)
                        .onlyRetrieveFromCache(true)
                )
                .into(holder.imgFoto); */
        holder.txtGenre.setText(list_data.get(position).get("tahun")+"  ("+list_data.get(position).get("jumlah")+")");

        holder.cvData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, movie_tahun_list.class);
                intent.putExtra(Konfigurasi.KUNCI_TAHUN, list_data.get(position).get("tahun"));
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
