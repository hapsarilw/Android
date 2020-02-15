package com.example.t0215037;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ViewHolder {
    protected TextView title;
    protected ListView lst_food;
    protected FoodListAdapter adapter;
    protected EditText et_title, et_deskripsi;
    protected Button btn_add;

    public ViewHolder (View view){
        title = view.findViewById(R.id.tv_nama);
        lst_food = view.findViewById(R.id.lst_foods);
        et_title = view.findViewById(R.id.et_title);
        et_deskripsi = view.findViewById(R.id.et_details);
        btn_add = view.findViewById(R.id.btn_add);

    }

    public void updateView(Food food){
        this.title.setText(food.getTitle());
    }
}
