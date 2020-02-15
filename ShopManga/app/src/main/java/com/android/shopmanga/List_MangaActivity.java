package com.android.shopmanga;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

public class List_MangaActivity extends AppCompatActivity {
    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__manga);

        final List<Manga> items = getIntent().getParcelableArrayListExtra("mangaList");

        final ArrayAdapter<Manga> adapter = new TodoAdapter(
                this,
                R.layout.activity_item_manga,
                items);
        ListView lv = findViewById(R.id.listView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(activity, MangadetailsActivity.class);
                intent.putExtra("manga", (Serializable) items.get(position));
                startActivity(intent);
            }
        });
        lv.setAdapter(adapter);
    }
}
