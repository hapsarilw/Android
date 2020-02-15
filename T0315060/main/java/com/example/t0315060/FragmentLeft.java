package com.example.t0315060;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentLeft extends Fragment implements View.OnClickListener {

    protected FragmentListener listener;
    protected Button btnHome, btnPage2, btnExit;

    public FragmentLeft() {

    }

    public static FragmentLeft newInstance() {
        FragmentLeft fragment = new FragmentLeft();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.listener = (FragmentListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);

        this.btnHome = view.findViewById(R.id.btn_home);
        this.btnPage2 = view.findViewById(R.id.btn_page2);
        this.btnExit = view.findViewById(R.id.btn_exit);

        this.btnHome.setOnClickListener(this);
        this.btnPage2.setOnClickListener(this);
        this.btnExit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == btnHome) {
            listener.changePage(1);
        }
        if (view == btnPage2) {
            listener.changePage(2);
        }
        if (view == btnExit) {
            listener.closeApplication();
        }
    }
}
