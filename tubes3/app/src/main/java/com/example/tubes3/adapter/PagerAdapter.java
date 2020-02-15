package com.example.tubes3.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tubes3.PageFragment;

import java.util.ArrayList;

public class PagerAdapter extends FragmentStatePagerAdapter implements PageFragment.ItemClick{
    ArrayList<String> url=new ArrayList<>();
    fragmentClickListener listener;
    public PagerAdapter(FragmentManager fm,ArrayList<String> imgurl) {
        super(fm);
        this.url=imgurl;
    }
    @Override
    public Fragment getItem(int position) {

            PageFragment fragment=new PageFragment();
            Bundle b=new Bundle();
            b.putString("URL",url.get(position));
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

    public void setFragmentListe(fragmentClickListener liste){
        listener=liste;
    }

    public interface fragmentClickListener{
        void OnClick(boolean var);
    }
}
