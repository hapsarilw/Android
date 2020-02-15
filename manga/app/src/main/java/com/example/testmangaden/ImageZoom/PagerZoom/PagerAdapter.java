package com.example.testmangaden.ImageZoom.PagerZoom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.testmangaden.Activity.Read_Pager;
import com.example.testmangaden.Detail_Comic.Chap;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter implements PagerFragment.ItemClick
{
    ArrayList<Chap> url=new ArrayList<>();
    fragmentClickListener listener;
    public PagerAdapter(FragmentManager fm, ArrayList<Chap> imgurl) {
        super(fm);
        Log.d("LoadListURL", "LoadListURL");
        this.url=imgurl;
    }
    @Override
    public Fragment getItem(int position) {

        PagerFragment fragment=new PagerFragment();
        Bundle b=new Bundle();
        Log.d("Input URL", position+":  "+url.get(position).getChap() + "");
        b.putString("URL",url.get(position).getChap());
        Read_Pager.mSeekBar.setProgress(position);
        Read_Pager.txt_index.setText((position)+"/"+url.size());
        fragment.setArguments(b);
        fragment.setItemListener(this);
        return fragment;
    }
    @Override
    public int getCount() {
        return url.size();
    }



    @Override
    public void OnClick(boolean position) {
        listener.OnClick(position);
    }
    public void setFragmentListe(Read_Pager listen){
        listener=listen;
    }
    public interface fragmentClickListener{
        public void OnClick(boolean var);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}