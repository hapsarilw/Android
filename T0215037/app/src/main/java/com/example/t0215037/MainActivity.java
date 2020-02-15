package com.example.t0215037;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected ListView lst_foods;
    protected FoodListAdapter food_adapter;
    protected EditText et_title, et_deskripsi;
    protected Button btn_add;
    private ViewHolder view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst_foods = this.findViewById(R.id.lst_foods);
        et_title = this.findViewById(R.id.et_title);
        et_deskripsi = this.findViewById(R.id.et_details);
        btn_add = this.findViewById(R.id.btn_add);

        btn_add.setOnClickListener(this);

        this.food_adapter = new FoodListAdapter(this);
        this.lst_foods.setAdapter(this.food_adapter);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_add) {
            String judul = et_title.getText().toString();
            String detail = et_deskripsi.getText().toString();
            Food newFood = new Food(judul, detail);
            this.food_adapter.addLine(newFood);
            et_title.setText("");
            et_deskripsi.setText("");
        }
    }
}
