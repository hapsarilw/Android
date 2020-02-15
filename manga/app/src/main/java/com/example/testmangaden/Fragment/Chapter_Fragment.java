package com.example.testmangaden.Fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.testmangaden.AdapterChapter.ChapterAdapter;
import com.example.testmangaden.Common.Common;
import com.example.testmangaden.Detail_Comic.Chapter;
import com.example.testmangaden.R;
import com.example.testmangaden.SwipeRecycler.SwipeController;
import com.example.testmangaden.SwipeRecycler.SwipeControllerActions;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

public class Chapter_Fragment extends Fragment {
    View view;
    ChapterAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionMenu actionMenu;
    FloatingActionButton increase;
    FloatingActionButton decrease;

    SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
        @Override
        public void onRightClicked(int position) {
            Toast.makeText(getContext(), list.get(position).getName(), Toast.LENGTH_SHORT).show();
            super.onRightClicked(position);
        }
    });
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
    List<Chapter> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_chapter, container, false);
        recyclerView = view.findViewById(R.id.recycler_chapter);
        adapter = new ChapterAdapter((ArrayList<Chapter>) list, getContext());
        final LinearLayoutManager layoutManager=  new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(true);
        actionMenu=  view.findViewById(R.id.fab_menu);
        increase= view.findViewById(R.id.fab_increase);
        decrease =view.findViewById(R.id.fab_decrease);
        actionMenu.setClosedOnTouchOutside(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c, getResources());
                increase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutManager.setReverseLayout(false);
                        layoutManager.scrollToPositionWithOffset(0, 0);
                    }
                });
                decrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layoutManager.setReverseLayout(true);
                        layoutManager.scrollToPositionWithOffset(list.size()-1, 0);
                    }
                });
            }
        });
        setHasOptionsMenu(true);
        return view;
    }






    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<List<String>> data = Common.selectedComic.getListchap();
        for (int i = data.size() - 1; i >= 0; i--) {
            list.add(new Chapter(data.get(i)));
            Log.d("MARKURL", data.get(i).get(0));
        }
//

    }
}
