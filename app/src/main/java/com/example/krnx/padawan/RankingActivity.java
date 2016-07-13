package com.example.krnx.padawan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by arnau on 05/07/16.
 */
public class RankingActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //findViewById del layout activity_ranking
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(this);

        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);


        //El adapter se encarga de  adaptar un objeto definido en el código a una vista en xml
        //según la estructura definida.
        //Asignamos nuestro custom Adapter
        mRecyclerView.setAdapter(new RankingAdapter(getApplicationContext()));
    }
}
