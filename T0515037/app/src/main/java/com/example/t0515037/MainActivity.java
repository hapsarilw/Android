package com.example.t0515037;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected ListView lstCounter;
    protected com.example.t0515037.CounterListAdapter adapter;
    protected FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstCounter = this.findViewById(R.id.lst_counter);
        btnAdd = this.findViewById(R.id.add_btn);

        btnAdd.setOnClickListener(this);

        adapter = new CounterListAdapter(this);
        lstCounter.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        if (v == btnAdd) {
            adapter.addLine();
        }
    }
}
