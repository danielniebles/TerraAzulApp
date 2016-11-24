package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends NavActivity {

    FloatingActionButton fab1;
    private RecyclerView recyclerView;
    private WebsAdapter adapter;
    private List<Webs> websList;
    private ArrayList<Carro> carros = new ArrayList<Carro>();
    private static final String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    //Usuario actual
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String name, email, UID, photo;
    private Firebase firebasedatos;
    int position = 0;
    long numero;
    private String[] titulo = new String[2];
    private String[] URLIm = new String[2];
    private String[] descripcion = new String[2];
    boolean created = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);

        Firebase.setAndroidContext(this);
        firebasedatos = new Firebase(FIREBASE_URL);

        //Guardar usuario en Firebase
        UID = user.getUid();
        name = user.getDisplayName();
        email = user.getEmail();
        photo = user.getPhotoUrl().toString();

        fab1 = (FloatingActionButton)findViewById(R.id.fab1);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MisReservasActivity.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        websList = new ArrayList<>();
        adapter = new WebsAdapter(this, websList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        firebasedatos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.child("Usuarios").child(UID).exists()){
                    Usuario usuario = new Usuario(UID, name, email, photo, carros);
                    firebasedatos.child("Usuarios").child(UID).setValue(usuario);


                }
                /*numero = dataSnapshot.child("Usuarios").child(UID).child("Carros").getChildrenCount();
                Toast.makeText(getApplicationContext(), Long.toString(numero), Toast.LENGTH_LONG).show();*/
                //Llenar el cardview con los ítems de Firebase
                for(int i = 0; i<=1;i++){
                    if(dataSnapshot.child("Noticias").child("noticia"+i).exists()){
                        titulo[i] = dataSnapshot.child("Noticias").child("noticia"+i).child("Titulo").getValue().toString();
                        descripcion[i] = dataSnapshot.child("Noticias").child("noticia"+i).child("Descripcion").getValue().toString();
                        //Toast.makeText(getApplicationContext(),titulo, Toast.LENGTH_SHORT).show();
                        URLIm[i] = dataSnapshot.child("Noticias").child("noticia"+i).child("URLIm").getValue().toString();
                    }
                }
                prepareWebs();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {   }
        });

        try {
            Glide.with(this).load(R.drawable.terracover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Noticias");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    private void prepareWebs() {

        if(created == false){
            Webs a = new Webs(titulo[0], URLIm[0], descripcion[0]);
            websList.add(a);
            a = new Webs(titulo[1], URLIm[1], descripcion[1]);
            websList.add(a);
            created = true;
        }else{
            websList.get(0).setName(titulo[0]);
            websList.get(0).setThumbnail(URLIm[0]);
            websList.get(0).setDescripcion(descripcion[0]);
            websList.get(1).setName(titulo[1]);
            websList.get(1).setThumbnail(URLIm[1]);
            websList.get(1).setDescripcion(descripcion[1]);
        }

        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
