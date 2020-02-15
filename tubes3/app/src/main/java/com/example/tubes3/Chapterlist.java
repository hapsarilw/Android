package com.example.tubes3;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import com.example.tubes3.adapter.ChapterAdapter;
import com.example.tubes3.model.Chapter;
import com.example.tubes3.model.ImageModel;

import com.tonyodev.fetch.Fetch;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class Chapterlist extends AppCompatActivity implements ChapterAdapter.viewchapter {
    private String url = "https://www.mangaeden.com/api/manga/";
    ProgressDialog gress;
    RecyclerView recyclerView;
    ImageView img;
    ChapterAdapter adapter;
    ProgressDialog bar;
    ArrayList<String> chapNo = new ArrayList<>();
    ArrayList<String> chapId = new ArrayList<>();
    static final int WRITE_PERMISSION = 1001;

    TextView genre;
    //    GridView listView;
    String msgdescription = "";
    CoordinatorLayout coordinatorLayout;
    Fetch fetch;
    TextView title, total_chapter, author, artist, released;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_chapterlist);

        bar = new ProgressDialog(this);
        bar.setIndeterminate(true);

        coordinatorLayout = findViewById(R.id.coordinator);
        img = findViewById(R.id.imageView);
        title = findViewById(R.id.textView);
        genre = findViewById(R.id.category2);
        recyclerView = findViewById(R.id.recyclerView2);
        adapter = new ChapterAdapter(chapNo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.parseColor("#BBBBBB"))
                        .sizeResId(R.dimen.divider)
                        .build());
        recyclerView.setAdapter(adapter);
        total_chapter = findViewById(R.id.textView6);
        author = findViewById(R.id.textView2);
        artist = findViewById(R.id.textView7);
        released = findViewById(R.id.textView4);
        url = url + getIntent().getStringExtra("ID");
        volleyCall();
        gress = new ProgressDialog(this);
        gress.setMessage("Memuat chapter");
        gress.setIndeterminate(true);
        fetch = Fetch.newInstance(getApplicationContext());
        new Fetch.Settings(getApplicationContext()).setConcurrentDownloadsLimit(2).apply();

        ChapterAdapter.setChapterlist(this);
        gress.show();
    }

    private void volleyCall() {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        Chapter chapter = new Chapter();
                        chapter = gson.fromJson(response.toString(), Chapter.class);
                        Glide.with(getApplicationContext()).load("https://cdn.mangaeden.com/mangasimg/" + chapter.getImage())
                                .apply(new RequestOptions()
                                        .override(154,250).centerCrop().placeholder(R.drawable.placeholder).error(R.drawable.placeholder))
                                .into(img);

                        title.setText(chapter.getTitle());
                        total_chapter.setText(chapter.getChapters_len() + " chapters");
                        author.setText("Author: " + chapter.getAuthor());
                        artist.setText("Artist: " + chapter.getArtist());
                        released.setText("Released: " + chapter.getReleased());
                        msgdescription = chapter.getDescription();
                        TextView textView = findViewById(R.id.description);
                        textView.setText(msgdescription);
                        StringBuffer stringBuffer = new StringBuffer();
                        for (String catgry : chapter.getCategories()) {
                            stringBuffer.append(catgry + " | ");
                        }
                        genre.setText(stringBuffer.toString());

                        Object ov[] = chapter.getChapters().toArray();
                        for (Object d : ov) {
                            Log.d("URL", d.toString() + 'd');
                            String red = d.toString().replace("[", "");
                            red = red.replace("]", "");
                            String newarray[] = red.split(",");
                            chapNo.add(newarray[0]);
                            chapId.add(newarray[newarray.length - 1]);
                        }
                        adapter.notifyDataSetChanged();
                        gress.dismiss();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        gress.dismiss();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }

    @Override
    public void viewChap(int position) {
        chapNo.get(position);
        Intent i = new Intent(getApplicationContext(), MangaViewer.class);
        i.putExtra("ID", chapId.get(position));
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // download task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Please Accept The Sd card permission.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
