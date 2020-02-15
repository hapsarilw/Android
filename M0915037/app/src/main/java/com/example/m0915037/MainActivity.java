package com.example.m0915037;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String EXTRA_MESSAGE = "com.example.m0915037.MESSAGE";
    Button startApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startApp = findViewById(R.id.start_app);
        startApp.setOnClickListener(this);
    }



    public void setStartApp() {
       Intent intent = new Intent(this, MainActivity2.class);
       startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view == startApp){
            setStartApp();
        }
    }
}
