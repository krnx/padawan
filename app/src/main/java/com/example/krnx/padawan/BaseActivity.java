package com.example.krnx.padawan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by inlab on 30/06/2016.
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "U04luD6D5qaCyInE4FDModK3e";
    private static final String TWITTER_SECRET = "2RHjSV533QBb59jLkWq2obU3UskrFTPLAT8lSQdcS4ztNsibDY";

    private NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setView();
    }

    protected void setView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.setDrawerListener(toggle);
        }
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Instanciamos el SharedPreferences
        SharedPreferences settings = getSharedPreferences("Padawan-prefs", 0);

        String useremail = settings.getString("email", "DEFAULT");

        View headerLayout = navigationView.getHeaderView(0);
        TextView userName = (TextView) headerLayout.findViewById(R.id.header_name);
        TextView userEmail = (TextView) headerLayout.findViewById(R.id.header_baseline);
        userEmail.setText(useremail);
        Log.v("Padawan", userName.getText().toString());
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.calculadora:
                startActivity(new Intent(getApplicationContext(), CalculadoraActivity.class));
                break;
            case R.id.ranking:
                startActivity(new Intent(getApplicationContext(), RankingActivity.class));
                break;
            case R.id.player:
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class));
                break;
            /*case R.id.sensor:
                startActivity(new Intent(getApplicationContext(), SensorActivity.class));
                break;
            case R.id.twitter:
                startActivity(new Intent(getApplicationContext(), TwitterActivity.class));
                break;*/
            case R.id.memory:
                startActivity(new Intent(getApplicationContext(), MemoryActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {

        DrawerLayout fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = (FrameLayout) fullLayout.findViewById(R.id.frame_layout_base);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        super.setContentView(fullLayout);
        setView();
    }

    public void checkMenuItem(int activity) {
        for (int i = 0; i < navigationView.getMenu().size(); ++i)
            navigationView.getMenu().getItem(i).setChecked(false);
        navigationView.getMenu().getItem(activity).setChecked(true);
    }
}
