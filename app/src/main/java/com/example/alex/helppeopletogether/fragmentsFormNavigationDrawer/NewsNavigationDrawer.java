package com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.SupportClasses.ConstantPreferences;
import com.example.alex.helppeopletogether.SupportClasses.Preferences;
import com.example.alex.helppeopletogether.registration.Login;
import com.example.alex.helppeopletogether.registration.Registration;

import de.hdodenhof.circleimageview.CircleImageView;


public class NewsNavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ConstantPreferences {
    Advertisement advertisement;
    Favorite favorite;
    NewsFragment news;
    PostAdvertisementFragment postAdvertisement;
    ExitFragment exit;
    TextView fullName;
    CircleImageView userImage;
    android.support.v4.app.FragmentManager fragmentManager;
    String name, foto, userId;
    Toolbar toolbar;
    Login login;
    Registration registration;
    Preferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_navigation_drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        advertisement = new Advertisement();
        preferences = new Preferences(NewsNavigationDrawer.this);
        favorite = new Favorite();
        news = new NewsFragment();
        postAdvertisement = new PostAdvertisementFragment();
        exit = new ExitFragment();
        login = new Login();
        registration = new Registration();
        name = preferences.loadText(PREFERENCES_NAME);
        foto = preferences.loadText(PREFERENCES_FOTO);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, news).commit();
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        fullName = (TextView) header.findViewById(R.id.navigation_drawer_full_name);
        userImage = (CircleImageView) header.findViewById(R.id.navigation_drawer_user_foto);
        Glide.with(NewsNavigationDrawer.this).load(foto).override(150, 150).into(userImage);
        fullName.setText(name);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("fullName", name);
        outState.putString("foto", foto);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fullName.setText(savedInstanceState.getString("fullName"));
        Glide.with(NewsNavigationDrawer.this).load(savedInstanceState.getString("foto")).override(150, 150).into(userImage);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            return true;

        }
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        return false;
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
            ftrans.replace(R.id.container, favorite);
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
