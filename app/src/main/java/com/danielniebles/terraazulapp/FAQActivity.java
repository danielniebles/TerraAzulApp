package com.danielniebles.terraazulapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class FAQActivity extends NavActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_faq);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.contenedorFrame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_faq, contentFrameLayout);
    }
}
