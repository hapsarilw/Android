package com.android.shopmanga;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.*;

public class myMangaDetailsActivity extends AppCompatActivity {

    private ImageView image;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_manga_details);
        context = this;
        String mangaId  = getIntent().getStringExtra("mangaId");
        Manga manga = MainActivity.appDatabase.mangaDao().loadById(mangaId);


        TextView mangaName = findViewById(R.id.myMangamangaName);
        TextView price = findViewById(R.id.myMangaprice);
        TextView sellerName = findViewById(R.id.myMangasellerName);
        TextView address = findViewById(R.id.myMangaaddress);
        TextView telephone = findViewById(R.id.myMangatelephone);
        TextView volume = findViewById(R.id.MyMangaVolume);
        image = findViewById(R.id.myMangaimageView);
        LoadImageFromUrl("https://cdn.mangaeden.com/mangasimg/"+ manga.getImageUrl());

        mangaName.setText( "Manga name :  "+manga.getMangaName());
        price.setText(     "Price :       "+Double.toString(manga.getPrice()));
        sellerName.setText("Seller Name : "+manga.getSellerName());
        address.setText(   "Addresse :    "+manga.getAddress());
        telephone.setText( "Telephone :   "+manga.getTelephone());
        volume.setText(    "Volume :      "+manga.getVolume()  );

        Button deleteButton = findViewById(R.id.myMangabutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(context);
                String url ="https://shopmangamobileapi.herokuapp.com/deleteManga";

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("name", manga.getMangaName());
                    jsonBody.put("volume", Double.toString(manga.getVolume()));
                    jsonBody.put("price", Double.toString(manga.getPrice()));
                    jsonBody.put("sellerName", manga.getSellerName());
                    jsonBody.put("adresse", manga.getAddress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toasty.success(context,"Operation succed",Toasty.LENGTH_LONG).show();
                        MainActivity.appDatabase.mangaDao().delete(manga);
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(context,"Cannot delete Manga",Toasty.LENGTH_LONG).show();

                    }
                }
                );
                queue.add(jsonObjectRequest);
            }
        });

    }
    private void LoadImageFromUrl(String url){
        Picasso.with(this).load(url)
                .error(R.mipmap.ic_launcher)
                .resize(720,720)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError() {

                    }
                });
    }
}
