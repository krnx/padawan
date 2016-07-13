package com.example.krnx.padawan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.krnx.padawan.db.rankingHelper;
import com.example.krnx.padawan.model.Ranking;
import com.example.krnx.padawan.model.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by arnau on 05/07/16.
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.AdapterViewHolder> {

    ArrayList<Ranking> players;

    private rankingHelper rankingHelper;

    RankingAdapter(Context context) {
        rankingHelper = new rankingHelper(context);
        Map<String, String> ranking = rankingHelper.getRanking();
        players = new ArrayList<>();

        Iterator it = ranking.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());

            String email = pair.getKey().toString();
            String points = pair.getValue().toString();

            players.add(new Ranking(email, Integer.valueOf(points)));
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Override
    public RankingAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.rowlayout_ranking, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingAdapter.AdapterViewHolder adapterViewholder, int position) {
//        int iconLayout = players.get(position).getIcon();
        //Dependiendo del entero se asignará una imagen u otra
//        switch (iconLayout){
//            case 0:
        //male
        adapterViewholder.icon.setImageDrawable(adapterViewholder.v.getResources().getDrawable(R.mipmap.avatar));
//                break;
//            case 1:
        //female
//                adapterViewholder.icon.setImageDrawable(adapterViewholder.v.getResources().getDrawable(R.mipmap.avatar));
//                break;
//        }
        adapterViewholder.email.setText(players.get(position).getEmail());
        adapterViewholder.points.setText(players.get(position).getPoints().toString());

    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return players.size();
    }


    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public ImageView icon;
        public TextView email;
        public TextView points;
        public View v;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.email = (TextView) itemView.findViewById(R.id.ranking_email);
            this.points = (TextView) itemView.findViewById(R.id.ranking_points);
        }
    }
}