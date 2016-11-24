package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class NavActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    int optlog = 0;
    Menu nav_menu;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String name, email;
    Uri photo;
    //ImageView profilephoto;
    CircularImageView profilephoto;
    TextView tNombren, tMailn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        nav_menu = navigationView.getMenu();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        drawerLayout = (DrawerLayout)findViewById(R.id.contenedorPrincipal);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abierto, R.string.cerrado);
        drawerLayout.setDrawerListener(drawerToggle);

        optlog = prefs.getInt("LogID", -1);

        View header = navigationView.getHeaderView(0);

        profilephoto = (CircularImageView)header.findViewById(R.id.imagend);
        tNombren = (TextView)header.findViewById(R.id.tNombren);
        tMailn = (TextView)header.findViewById(R.id.tMailn);

        name = user.getDisplayName();
        email = user.getEmail();
        tMailn.setText(email);
        tNombren.setText(name);
        photo = user.getPhotoUrl();

        //Toast.makeText(getApplicationContext(), photo.toString().replace("100x100", "400x400"), Toast.LENGTH_LONG).show();
        Picasso.with(getApplicationContext()).load(photo.toString()).into(profilephoto);


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
                    case R.id.nav_cerrar:
                        signOut();
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

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        if(optlog == 1) {
            // Google sign out
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            Intent intent6 = new Intent(getApplicationContext(), LogginActivity.class);
                            startActivity(intent6);
                        }
                    });
        }else if(optlog == 2){
            LoginManager.getInstance().logOut();
            Intent intent6 = new Intent(getApplicationContext(), LogginActivity.class);
            startActivity(intent6);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
