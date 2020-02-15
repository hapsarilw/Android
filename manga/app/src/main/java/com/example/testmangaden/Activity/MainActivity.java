package com.example.testmangaden.Activity;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.testmangaden.Common.Common;
import com.example.testmangaden.Interface.GetNoticeDataService;
import com.example.testmangaden.Manga.Manga;
import com.example.testmangaden.Manga.MangaAdapter;
import com.example.testmangaden.Manga.MangaList;
import com.example.testmangaden.R;

import java.util.ArrayList;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    MangaAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    GetNoticeDataService service;
    CompositeDisposable compositeDisposable= new CompositeDisposable();

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id== R.id.action_search) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!searchView.isIconified())
        {
             searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        SearchManager searchManager= (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.recycler_view_notice_list);
        Toolbar toolbar= findViewById(R.id.toolBar);
        service= Common.getAPI();
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       getSupportActionBar().setTitle("Manga....");
        fetchmanga();
    }

    private void fetchmanga() {

        final AlertDialog dialog =new SpotsDialog.Builder().setContext(this).setMessage("Please waiting....").setCancelable(false).build();
        dialog.show();
 compositeDisposable.add(service.getNoticeData().subscribeOn(Schedulers.io())
 .observeOn(AndroidSchedulers.mainThread())
 .subscribe(new Consumer<MangaList>() {
     @Override
     public void accept(MangaList mangaList) throws Exception {
        generateNoticeList(mangaList.getNoticeArrayList());
        dialog.dismiss();
     }
 }, new Consumer<Throwable>() {
     @Override
     public void accept(Throwable throwable) throws Exception {
         Toast.makeText(MainActivity.this,"Error loading data", Toast.LENGTH_LONG).show();
         dialog.dismiss();
     }
 }));
    }
    private void generateNoticeList(ArrayList<Manga> noticeArrayList) {
            adapter= new MangaAdapter(noticeArrayList);
            RecyclerView.LayoutManager manager= new GridLayoutManager(MainActivity.this,2);
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
    }
}
