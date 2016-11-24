package com.danielniebles.terraazulapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.cardemulation.CardEmulation;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Daniel on 18/11/2016.
 */

public class WebsAdapter extends RecyclerView.Adapter<WebsAdapter.MyViewHolder>{
    private Context mContext;
    private List<Webs> websList;
    public int position;
    private static final String FIREBASE_URL="https://terraazul-cd8d8.firebaseio.com/";
    private Firebase firebasedatos;
    String titulo, url;
    boolean clicked = false;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, count;
        public ImageView thumbnail;
        public CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            mCardView = (CardView) view.findViewById(R.id.card_view);
            thumbnail.setOnClickListener(this);
        }

        public void onClick(View view) {
            position = getAdapterPosition();
            clicked = true;
            Firebase.setAndroidContext(mContext);
            firebasedatos = new Firebase(FIREBASE_URL);

            switch (view.getId()){
                case R.id.thumbnail:
                    firebasedatos.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("Noticias").child("noticia"+position).exists()){
                                url = dataSnapshot.child("Noticias").child("noticia"+position).child("URL").getValue().toString();
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                if (clicked == true){
                                    mContext.startActivity(i);
                                    clicked = false;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(FirebaseError firebaseError) {    }
                    });
                    break;
            }
        }
    }

    public WebsAdapter(Context mContext, List<Webs> websList) {
        this.mContext = mContext;
        this.websList = websList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Webs Webs = websList.get(position);
        holder.title.setText(Webs.getName());
        holder.count.setText(Webs.getDescripcion());

        holder.mCardView.setTag(position);
        // loading Webs cover using Glide library
        //Glide.with(mContext).load(Webs.getThumbnail()).into(holder.thumbnail);
        Picasso.with(mContext).load(Webs.getThumbnail()).into(holder.thumbnail);

        //Toast.makeText(mContext, Integer.toString(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return websList.size();
    }


}
