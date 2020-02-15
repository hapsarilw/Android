package com.example.t0515037;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CounterListAdapter extends BaseAdapter {

    protected List<String> listItems;
    private Activity activity;

    public CounterListAdapter(Activity activity) {
        this.activity = activity;
        this.listItems = new ArrayList<String>();
    }

    public void addLine() {
        this.listItems.add("0");
        this.notifyDataSetChanged();
    }

    public void updateLine(int position, String msg) {
        listItems.set(position, msg);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public String getItemString(int position) {
        return (String) this.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(this.activity).inflate(R.layout.counter_list_item, parent, false);
        final String currentFood = (String) this.getItem(position);

        TextView tvCounter = convertView.findViewById(R.id.tv_counter);
        final Button btnStart = convertView.findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnStart.getText().toString().equals("Start")) {
                    btnStart.setText("Stop");

                    CounterAsyncTask cat = new CounterAsyncTask();
                    cat.execute(10);


                } else {
                    btnStart.setText("Start");
                }
            }
        });

        return convertView;


    }
}
