package com.example.m0715037;

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
    TextView tvHasil;
    EditText etOperasi;
    String result;
    //Some url endpoint that you may have
    String myUrl = "http://api.mathjs.org/v4/?expr=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnHitung = findViewById(R.id.btn_hitung);
        this.tvHasil = findViewById(R.id.tv_hasil);
        this.etOperasi = findViewById(R.id.et_operasi);

        btnHitung.setOnClickListener(this);


        // Put a message in the text view
        tvHasil.setText("0");
    }

    @Override
    public void onClick(View view) {
        if (view == btnHitung){
            myUrl = myUrl.concat(etOperasi.getText().toString());
            new HttpGetRequest(tvHasil).execute(myUrl);
            Log.d(myUrl, "cek");
        }
    }

    private class HttpGetRequest extends AsyncTask<String, Void, String> {
        private TextView textView;

        public HttpGetRequest(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "not operate";
            try {
                URL url = new URL(myUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }
                urlConnection.disconnect();
                return "jalan";

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String temp) {
            textView.setText(temp);
        }
    }


}
