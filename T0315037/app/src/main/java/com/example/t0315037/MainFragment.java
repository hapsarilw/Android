package com.example.t0315037;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainFragment extends Fragment implements View.OnClickListener {

    protected FragmentListener listener;
    protected EditText etText;
    protected Button btnClick;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (FragmentListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.etText = view.findViewById(R.id.et_text);
        this.btnClick = view.findViewById(R.id.btn_click);

        this.btnClick.setOnClickListener(this);

        return view;

    }

    public String getText() {
        return etText.getText().toString();
    }

    @Override
    public void onClick(View view) {
        if (view == btnClick) {
            listener.setText();
        }
    }
}
