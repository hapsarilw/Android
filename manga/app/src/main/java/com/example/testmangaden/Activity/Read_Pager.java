package com.example.testmangaden.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.testmangaden.AdapterChapter.ListViewAdapter;
import com.example.testmangaden.Common.Common;
import com.example.testmangaden.Detail_Comic.Chap;
import com.example.testmangaden.Detail_Comic.detail_chap;
import com.example.testmangaden.ImageZoom.ImageZoomViewPager;
import com.example.testmangaden.ImageZoom.PagerZoom.PagerAdapter;
import com.example.testmangaden.ImageZoom.ZoomOutPageTransformer;
import com.example.testmangaden.Interface.GetNoticeDataService;
import com.example.testmangaden.MySingleton;
import com.example.testmangaden.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Read_Pager extends FragmentActivity implements PagerAdapter.fragmentClickListener {
    ImageZoomViewPager zoomViewPager;
    PagerAdapter adapter;
    BottomNavigationView view_bottom;
    ArrayList<Chap> imagelist= new ArrayList<>();
    public static SeekBar mSeekBar;
    RelativeLayout menutab;
    GetNoticeDataService service;
    TextView txt_chapter_index;
    View chapter_back;
    View chapter_next;
    Context context;
    int size;
    ListViewAdapter listViewAdapter;
    public static TextView txt_index;
    Read_Pager read_pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_pager);
        chapter_back= findViewById(R.id.chapter_back);
        chapter_next= findViewById(R.id.chapter_next);
        txt_chapter_index =findViewById(R.id.txt_chapter_index);
        zoomViewPager= (ImageZoomViewPager) findViewById(R.id.viewpager);
        mSeekBar=(SeekBar) findViewById(R.id.sb_bar);
        zoomViewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        txt_index=(TextView) findViewById(R.id.txt_index);
        service= Common.getAPI();
        context=getApplicationContext();
        read_pager=this;
        chapter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.selectedIndex==0)
                {
                    Toast.makeText(Read_Pager.this,"FIRST CHAPTER", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Common.selectedIndex--;
                    Common.selectedChapter=Common.sourceChapter.get(Common.selectedIndex);
                    fetchdata();
                }

            }
        });
        chapter_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.selectedIndex==Common.sourceChapter.size()-1)
                {
                    Toast.makeText(Read_Pager.this,"LAST CHAPTER", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Common.selectedIndex++;
                    Common.selectedChapter=Common.sourceChapter.get(Common.selectedIndex);
                    fetchdata();
                }

            }
        });
        fetchdata();


    }
    private void fetchdata()
    {
        txt_chapter_index.setText("CHAPTER "+Common.selectedChapter.getNum());
        Call<detail_chap> call=service.getChap(Common.selectedChapter.getID());
        Log.d("LISTIMG", call.request().toString());
        call.enqueue(new Callback<detail_chap>() {
            @Override
            public void onResponse(Call<detail_chap> call, Response<detail_chap> response) {
                Log.d("LISTIMG", String.valueOf(response.body().getList().size()));
                imagelist = (ArrayList<Chap>) response.body().getList();
                size= imagelist.size();
                adapter =new PagerAdapter(getSupportFragmentManager(),imagelist);
                mSeekBar.setMax(size);
                txt_index.setText(1+"/"+(size));
                mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int progress = seekBar.getProgress() <= 0 ? 0 : seekBar.getProgress();
                        zoomViewPager.setCurrentItem(progress);
                        txt_index.setText((progress)+"/"+(size));
                    }
                });
                zoomViewPager.setAdapter(adapter);
                adapter.setFragmentListe(read_pager);
                callvolley();

            }

            @Override
            public void onFailure(Call<detail_chap> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                Log.d("ERRORCHAPTER", t.getMessage());

            }
        });

    }
    private void callvolley() {
        StringRequest req=new StringRequest(Request.Method.GET, "", new com.android.volley.Response.Listener<String>()
        {

            @Override
            public void onResponse(String response)
            {

                listViewAdapter.setItemSelectListener(new ListViewAdapter.ItemSelector() {
                    @Override
                    public void OnSelect(int position) {
                        zoomViewPager.setCurrentItem(position);
                        txt_index.setText((position)+"/"+(size));
                        mSeekBar.setProgress(position);

                    }
                });
                adapter.notifyDataSetChanged();
            }
        },
                new com.android.volley.Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("SEEKBAR", error.getMessage());
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(req);
    }
    @Override
    public void onBackPressed() {
        // If the user is currently looking at the first step, allow the system to handle the
        // Back button. This calls finish() on this activity and pops the back stack.
        super.onBackPressed();
    }


    @Override
    public void OnClick(boolean var) {
        view_bottom = (BottomNavigationView) findViewById(R.id.bottom_bar);
        menutab =(RelativeLayout) findViewById(R.id.menutab);
        Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_up);
        Animation bottomDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_down);
        if (view_bottom.getVisibility() == View.VISIBLE) {
            view_bottom.startAnimation(bottomDown);
            //hide it
            view_bottom.setVisibility(View.INVISIBLE);
        } else {
            //show it
            view_bottom.startAnimation(bottomUp);
            view_bottom.setVisibility(View.VISIBLE);
        }
    }

}
