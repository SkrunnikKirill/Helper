package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.alex.helppeopletogether.R;

import java.util.Calendar;


public class NewsNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Advertisement advertisement;
    FriendFragment friend;
    MessageFragment message;
    NewsFragment news;
    PostAdvertisementFragment postAdvertisement;
    ExitFragment exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //   savedInstanceState= intent5.getStringExtra();
        //  setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        advertisement = new Advertisement();
        friend = new FriendFragment();
        message = new MessageFragment();
        news = new NewsFragment();
        postAdvertisement = new PostAdvertisementFragment();
        exit = new ExitFragment();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.news_navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_my_advertisement) {
            ftrans.replace(R.id.container, advertisement);
        } else if (id == R.id.nav_my_friend) {
            ftrans.replace(R.id.container, friend);
        } else if (id == R.id.nav_my_message) {
            ftrans.replace(R.id.container, message);
        } else if (id == R.id.nav_news) {
            ftrans.replace(R.id.container,news);
        } else if (id == R.id.nav_post_advertisement) {
            ftrans.replace(R.id.container,postAdvertisement);
        } else if (id == R.id.nav_exit) {
            ftrans.replace(R.id.container, exit);
        }ftrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
