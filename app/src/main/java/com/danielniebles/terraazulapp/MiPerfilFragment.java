package com.danielniebles.terraazulapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class MiPerfilFragment extends Fragment {


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String email, name;
    Uri photo;
    CircularImageView profilephoto;
    TextView nombre, correo;

    public MiPerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mi_perfil, container, false);

        profilephoto = (CircularImageView)view.findViewById(R.id.profile);
        nombre = (TextView)view.findViewById(R.id.nombreuser);
        correo = (TextView)view.findViewById(R.id.emailuser);

        name = user.getDisplayName();
        email = user.getEmail();
        photo = user.getPhotoUrl();

        //Toast.makeText(getActivity(), photo.toString().replace("100x100", "200x200"), Toast.LENGTH_LONG).show();
        Picasso.with(getActivity()).load(photo.toString()).into(profilephoto);
        nombre.setText(name);
        correo.setText(email);

        return view;
    }

}
