package com.example.t0215037;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends BaseAdapter {
    private List<String> listItems;
    private List<String> listDeskripsi;
    private Activity activity;

    public TestAdapter(Activity activity) {
        this.activity = activity;
        this.listItems = new ArrayList<String>();
        this.listDeskripsi = new ArrayList<String>();
    }

    public void addLine(String newItem, String deskripsi) {
        this.listItems.add(newItem);
        this.listDeskripsi.add(deskripsi);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = this.activity.getLayoutInflater().inflate(R.layout.food_list_item, null);
        TextView tvNama = itemView.findViewById(R.id.tv_nama);
        TextView tvDeskripsi = itemView.findViewById(R.id.tv_details);
        tvNama.setText(this.listItems.get(i));
        tvDeskripsi.setText(this.listDeskripsi.get(i));
        return itemView;
    }
}
