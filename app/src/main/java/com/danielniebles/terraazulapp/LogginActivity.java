package com.danielniebles.terraazulapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LogginActivity extends AppCompatActivity {

    ImageButton bFacebook;
    TextView tContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        bFacebook = (ImageButton)findViewById(R.id.bFacebook);
        tContinuar = (TextView)findViewById(R.id.tContinuar);

        bFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Loggin2Activity.class);
                startActivity(intent);
            }
        });

        tContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Loggin2Activity.class);
                startActivity(intent);
            }
        });
    }
}
