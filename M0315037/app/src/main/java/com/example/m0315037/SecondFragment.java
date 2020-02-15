package com.example.m0315037;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//handle interaksi pada layout fragment_first
public class SecondFragment extends Fragment implements View.OnClickListener{
    protected TextView tvTitle;
    protected EditText et_message;
    protected FragmentListener listener;
    protected Button btn_submit;
    public SecondFragment(){

    }

    //Argumen yang disimmpan pada fragment digunakan dgn mengambil data sesuai key pd "this.tvTitle.setText(this.getArguments().getString("title", ""))"
    public static SecondFragment newInstance(String title){
        SecondFragment fragment = new SecondFragment();
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
        View view =  inflater.inflate(R.layout.fragment_second, container, false);


        //findViewById
        this.tvTitle = view.findViewById(R.id.tv_title);
        this.btn_submit = view.findViewById(R.id.btn_submit);
        this.et_message = view.findViewById(R.id.et_message);

        this.btn_submit.setOnClickListener(this);

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
        listener.changePage(1);
        listener.changeMessage(et_message.getText().toString());
        this.et_message.setText("");
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
//    }



}
