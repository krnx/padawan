package com.example.krnx.padawan;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by inlab on 07/07/2016.
 */
public class MemoryActivity extends BaseActivity implements View.OnClickListener {
    CoolImageFlipper imatge;
    ImageView card[];
    View carta;
    Integer memo[];
    Integer girada;
    List<Integer> estat;
    Map<Integer, Integer> cardMap;
    Integer intents;
    Boolean clicable = true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        imatge = new CoolImageFlipper(getApplicationContext());
        card = new ImageView[16];
        memo = new Integer[16];
        cardMap = new HashMap<Integer, Integer>();
        intents = 0;

        memo[0] = memo[8] = R.mipmap.c3po;
        memo[1] = memo[9] = R.mipmap.r2d2;
        memo[2] = memo[10] = R.mipmap.bobafet;
        memo[3] = memo[11] = R.mipmap.leia;
        memo[4] = memo[12] = R.mipmap.jedi;
        memo[5] = memo[13] = R.mipmap.darth;
        memo[6] = memo[14] = R.mipmap.ewok;
        memo[7] = memo[15] = R.mipmap.chewaka;
//        memo[8] = R.mipmap.hansolo;
//        memo[9] = R.mipmap.pilot;
//        memo[10] = R.mipmap.itsatrap;
//        memo[11] = R.mipmap.stormtrooper;
//        memo[12] = R.mipmap.emperor;
//        memo[13] = R.mipmap.obi;
//        memo[14] = R.mipmap.narco;
//        memo[15] = R.mipmap.robot;

        List<Integer> newMemo = new ArrayList<Integer>(Arrays.asList(memo));
        Collections.shuffle(newMemo);


        for (int i = 0; i < 16; i++) {
            card[i] = (ImageView) findViewById(getResources().getIdentifier("memory_card" + i, "id", MemoryActivity.this.getPackageName()));
            card[i].setOnClickListener(this);
            cardMap.put(card[i].getId(), newMemo.get(i));
        }

        estat = new ArrayList<Integer>();
        girada = null;
    }

    @Override
    public void onClick(View view) {
        Log.v("Padawan-memory",estat.toString());
        Log.v("Padawan-memory",String.valueOf(view.getId()));
        if(!estat.contains(Integer.valueOf(view.getId())) && clicable) {
            carta = view;
            if (girada == null) {
                intents++;
                imatge.flipImage(getDrawable(cardMap.get(view.getId())), (ImageView) view);
                girada = view.getId();
            } else {
                if (!girada.equals(view.getId())) {
                    clicable = false;
                    intents++;
                    imatge.flipImage(getDrawable(cardMap.get(view.getId())), (ImageView) view);
                    if (cardMap.get(girada) != cardMap.get(view.getId())) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                imatge.flipImage(getDrawable(R.mipmap.avatar), (ImageView) carta);
                                imatge.flipImage(getDrawable(R.mipmap.avatar), (ImageView) findViewById(girada));
                                girada = null;
                                clicable = true;
                            }
                        }, 1500);
                    } else {
                        estat.add(view.getId());
                        estat.add(girada);
                        if(Integer.valueOf(estat.size()).equals(16)) {
                            Toast.makeText(this, "S'ha acabat la partida. tens " + intents + " punts", Toast.LENGTH_LONG).show();
                            //TODO: Introduir els intents a la bbdd
                        }
                        girada = null;
                        clicable = true;
                    }
                }
            }
        }
    }
}