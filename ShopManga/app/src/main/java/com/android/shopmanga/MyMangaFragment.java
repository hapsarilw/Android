
package com.android.shopmanga;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;


public class MyMangaFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_manga, container, false);

        final List<Manga> items = MainActivity.appDatabase.mangaDao().getAll();

        final ArrayAdapter<Manga> adapter = new TodoAdapter(
                getContext(),
                R.layout.activity_item_manga,
                items);
        ListView lv = view.findViewById(R.id.MyMangaListView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), myMangaDetailsActivity.class);
                intent.putExtra("mangaId", items.get(position).getId());
                startActivity(intent);
            }
        });
        lv.setAdapter(adapter);
        return view;
    }
}
