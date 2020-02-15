package com.example.m0315037;

import android.content.Context;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

//handle interaksi pada layout fragment_first
public class FirstFragment extends Fragment implements View.OnClickListener {
    protected TextView tvTitle, tvMesaage;
    protected FragmentListener listener;
    protected Button btn_page;
    public FirstFragment(){

    }

    //Argumen yang disimmpan pada fragment digunakan dgn mengambil data sesuai key pd "this.tvTitle.setText(this.getArguments().getString("title", ""))"
    public static FirstFragment newInstance(String title){
        FirstFragment fragment = new FirstFragment();
        //Membutuhkan data tambahan dari activity untuk proses internal/
        //memandaatkan argumen pada fragment yang dipasang ketika mengeksekusi newinstance.

        //Bundle : menyimpan kumpulan data
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Layout tampilan untuk fragment ini
        View view =  inflater.inflate(R.layout.fragment_first, container, false);


        //findViewById
        this.tvTitle = view.findViewById(R.id.tv_title);
        this.btn_page = view.findViewById(R.id.btn_page);
        this.tvMesaage = view.findViewById(R.id.tv_message);

        this.btn_page.setOnClickListener(this);

        //changeText
        this.tvTitle.setText(this.getArguments().getString("title", ""));

        return view;
    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener) {
            this.listener = (FragmentListener) context;
        }
        else{
            throw new ClassCastException(context.toString()
            + "must implement FragmentListener"
            );
        }
    }

    public void onClick(View view){
        listener.changePage(2);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
//    }



}
