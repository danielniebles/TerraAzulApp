package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NavActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Menu nav_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        nav_menu = navigationView.getMenu();

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }*/

        drawerLayout = (DrawerLayout)findViewById(R.id.contenedorPrincipal);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abierto, R.string.cerrado);
        drawerLayout.setDrawerListener(drawerToggle);


        if(prefs.getInt("Invitado", -1) == 1){
            nav_menu.getItem(R.id.nav_reservas).setVisible(false);
        }

        View header = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_inicio:
                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_faq:
                        Intent intent2 = new Intent(getApplicationContext(), FAQActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_mobiliario:
                        Intent intent3 = new Intent(getApplicationContext(), MobiliarioActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_reservas:
                        Intent intent4 = new Intent(getApplicationContext(), MisReservasActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_perfil:
                        Intent intent5 = new Intent(getApplicationContext(), MiPerfilActivity.class);
                        startActivity(intent5);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
