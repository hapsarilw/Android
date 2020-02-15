package com.example.t0615037;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnHitung;
    protected TextView tvEkspresi, tvPrecision, tvHasil;
    EditText etEkspresi, etPrecision;
    String result;
    //Some url endpoint that you may have
    String myUrl = "http://api.mathjs.org/v4/?expr=";
    PostCalculateTask postCalculateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnHitung = findViewById(R.id.btn_hitung);
        this.tvHasil = findViewById(R.id.tv_hasil);

        this.tvEkspresi = findViewById(R.id.tv_ekspresi);
        this.etEkspresi = findViewById(R.id.et_ekspresi);
        this.tvPrecision = findViewById(R.id.tv_precision);
        this.etPrecision = findViewById(R.id.et_precision);

        btnHitung.setOnClickListener(this);

        // Put a message in the text view
        tvHasil.setText("0");
    }

    @Override
    public void onClick(View view) {
        if (view == btnHitung){
            myUrl = myUrl.concat(etEkspresi.getText().toString());
            String eksp = etEkspresi.getText().toString();
            int precision = Integer.parseInt(etPrecision.getText().toString());

            String[] s = new String[10];
            s[0] = eksp;
            postCalculateTask.execute(s, precision);
        }
    }



}
