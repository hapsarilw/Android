package com.example.t0615037;


import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostCalculateTask {
    private String BASE_URL = "http://api.mathjs.org/v4/";
    private IMainActivity iMainActivity;
    private static Context mContext;
    private Gson gson;


   public void execute(String[] expr, int precision){
       //Berisi membuat objek Input berdasarkan parameter expr dan precision
       Input input = new Input(expr, precision);
       Log.d("execute", "masuk1");

       //mengubah objek Input ke dalam bentuk string json menggunakan GSON
       gson = new Gson();
       String json = gson.toJson(input);
       try{
           JSONObject obj = new JSONObject(json);
           Log.d("My App", obj.toString());
           //panggil method callVolley
           callVolley(obj);
       } catch(Throwable t){
           Log.e("My App", "Could not parse malformed JSON: \"" + json + "\"");
       }
   }

    public void callVolley(JSONObject obj){
        //pemanggilan web service dengan menggunakan library Volley (POST)
        RequestQueue queue = Volley.newRequestQueue(mContext);
        JSONObject obj1 = obj;
        //1. URL dari BASE_URL
        BASE_URL = BASE_URL+obj1.toString();
        //2. input = parameter
        //3. response listener : panggil processResult((response listener dijalankan di UI Thread)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL, obj1, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                processResult(response.toString());
                Log.d("RESPONSE", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RESPONSE", "That didn't work!");
            }
        });
        queue.add(request);
    }

    public void processResult(String json){
        // Mengubah string json ke Kelas Result menggunakan GSON (Pelajari :
        // https://github.com/google/gson).
        Result res = gson.fromJson(json, Result.class);

        // Jika tidak terdapat error, maka lakukan pemrosesan data
        // dan ditampilkan pada TextView.
        res.getError();
        MainActivity mainActivity = new MainActivity();
        mainActivity.tvHasil.setText(res.getResult().toString());

    }

}
