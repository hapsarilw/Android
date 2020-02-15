package com.example.testmangaden.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.testmangaden.Common.Common;
import com.example.testmangaden.Detail_Comic.Detail_Comic;
import com.example.testmangaden.Fragment.Chapter_Fragment;
import com.example.testmangaden.Fragment.Comic_Fragment;
import com.example.testmangaden.Fragment.ViewPagerAdapter;
import com.example.testmangaden.Interface.GetNoticeDataService;
import com.example.testmangaden.R;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class detail_comic extends AppCompatActivity {
    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();

    }
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    GetNoticeDataService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_comic);
         tabLayout= (TabLayout) findViewById(R.id.tablayout_id);
         viewPager=(ViewPager) findViewById(R.id.view_pager);
         service= Common.getAPI();
        Call<Detail_Comic> call =service.getComic(Common.selectedManga.getI());
        Log.d("TaskRequest", call.request().toString());
        final AlertDialog dialog =new SpotsDialog.Builder().setContext(this).setMessage("Please wait....").setCancelable(false).build();
        dialog.show();
        call.enqueue(new Callback<Detail_Comic>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(Call<Detail_Comic> call, Response<Detail_Comic> response) {
                Common.selectedComic=response.body();
                adapter =new ViewPagerAdapter(getSupportFragmentManager());
                adapter.AddFragment(new Comic_Fragment(Common.selectedComic),"");
                adapter.AddFragment(new Chapter_Fragment(),"");
                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                Log.d("MARK", String.valueOf(Common.selectedComic.getListchap().size()));
                tabLayout.getTabAt(0).setIcon(R.drawable.ic_inbox_black_24dp);
                tabLayout.getTabAt(1).setIcon(R.drawable.ic_format_list_bulleted_black_24dp);
                dialog.dismiss();
                ColorStateList colors;
                if (Build.VERSION.SDK_INT >= 23) {
                    colors = getResources().getColorStateList(R.drawable.tab_color, getTheme());
                }
                else {
                    colors = getResources().getColorStateList(R.drawable.tab_color);
                }

                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i);
                    Drawable icon = tab.getIcon();

                    if (icon != null) {
                        icon = DrawableCompat.wrap(icon);
                        DrawableCompat.setTintList(icon, colors);
                    }
                }
            }
            @Override
            public void onFailure(Call<Detail_Comic> call, Throwable t) {
             dialog.dismiss();
            }
        });


    }

    private void fectchcomic() {
        final AlertDialog dialog =new SpotsDialog.Builder().setContext(this).setMessage("Please wait....").setCancelable(false).build();
        dialog.show();
        compositeDisposable.add(service.getDetailComic(Common.selectedManga.getI()).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Detail_Comic>() {
            @Override
            public void accept(Detail_Comic comic) throws Exception {
                Common.selectedComic=comic;
                Log.d("GOGOGO", comic.getDescription());
                dialog.dismiss();
                adapter.notifyDataSetChanged();
                Toast.makeText(detail_comic.this,comic.getAuthor(), Toast.LENGTH_LONG).show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(detail_comic.this,"Error loading data", Toast.LENGTH_LONG).show();
                Log.d("GOGOGO", "ERROR");
                dialog.dismiss();

            }
        }));
    }
}
