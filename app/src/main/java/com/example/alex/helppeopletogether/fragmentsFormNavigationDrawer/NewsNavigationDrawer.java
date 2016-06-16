package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;


public class NewsNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Advertisement advertisement;
    MyAdvertisement message;
    NewsFragment news;
    PostAdvertisementFragment postAdvertisement;
    ExitFragment exit;
    TextView fullName;
    ImageView userImage;
    android.support.v4.app.FragmentTransaction ft;
    android.support.v4.app.FragmentManager fragmentManager;
    String name, foto, userId;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        advertisement = new Advertisement();
        message = new MyAdvertisement();
        news = new NewsFragment();
        postAdvertisement = new PostAdvertisementFragment();
        exit = new ExitFragment();
        Intent intentFullName = getIntent();
        name = intentFullName.getStringExtra("fullName");
        foto = intentFullName.getStringExtra("foto");


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container, news).commit();
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        fullName = (TextView) header.findViewById(R.id.navigation_drawer_full_name);
        userImage = (ImageView)header.findViewById(R.id.navigation_drawer_user_foto);
        Glide.with(getApplicationContext()).load(foto).override(150, 150).into(userImage);
        fullName.setText(name);
        navigationView.setNavigationItemSelectedListener(this);


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




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_my_advertisement) {
            toolbar.setTitle("Мои Объявления");
            ftrans.replace(R.id.container, advertisement);
        } else if (id == R.id.nav_my_message) {
            toolbar.setTitle("Избранные");
            ftrans.replace(R.id.container, message);
        } else if (id == R.id.nav_news) {
            toolbar.setTitle("Новости");
            ftrans.replace(R.id.container, news);
        } else if (id == R.id.nav_post_advertisement) {
            toolbar.setTitle("Разместить объявление");
            ftrans.replace(R.id.container, postAdvertisement);
        } else if (id == R.id.nav_exit) {
            ftrans.replace(R.id.container, exit);
        }
        ftrans.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
