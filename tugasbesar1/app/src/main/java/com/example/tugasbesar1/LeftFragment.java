package com.example.tugasbesar1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.tugasbesar1.R.layout.list_nav_drawer;


public class LeftFragment extends Fragment implements FragmentListener{
    ListView lst_nav;
    FragmentManager fragmentManager;
    CalculatorFragment calculatorFragment;
    Adapter adapter;
//    DrawerLayout dl;
    AddFragment addFragment;


    public LeftFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        this.lst_nav = view.findViewById(R.id.lst_menu);
//        this.dl = view.findViewById(R.id.drawer_layout);

        String[] navigation = {"HOME", "ADD OPERATION", "HISTORY"};
        this.lst_nav.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, navigation));
        lst_nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("debug", "clicked");
                String page = lst_nav.getItemAtPosition(i)+"";
                Log.d("debug", page);
                if(page.equals("HOME")){
                    changePage(1);
                    Log.d("debug", "change1");
                    ((MainActivity)getActivity()).closeApplication();
                }
                if(page.equals("ADD OPERATION")){
                    changePage(2);
                    Log.d("debug", "change2");
                    ((MainActivity)getActivity()).closeApplication();
                }
            }
        });

        this.calculatorFragment = new CalculatorFragment();
        this.addFragment = new AddFragment();
        this.fragmentManager = getFragmentManager();



        return view;
    }


    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(page == 1){
            if(this.calculatorFragment.isAdded()){
                ft.show(this.calculatorFragment);
            }else{
                ft.add(R.id.fragment_container, this.calculatorFragment);
            }
            if(this.addFragment.isAdded()){
                ft.hide(this.addFragment);
            }
        }else if(page == 2){
            if(this.addFragment.isAdded()){
                ft.show(this.addFragment);
            }else {
                ft.add(R.id.fragment_container, this.addFragment);
            }
            if(this.calculatorFragment.isAdded()){
                ft.hide(this.calculatorFragment);
            }
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void addList(String number) {

    }
    @Override
    public void clearList() {

    }

    @Override
    public void closeApplication() {
        getActivity().moveTaskToBack(true);
        getActivity().finish();
    }


}