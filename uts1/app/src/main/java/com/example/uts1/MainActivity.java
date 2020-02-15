package com.example.uts1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected ListView lst_food;
    protected EditText et_title, et_detail;
    protected Button btn_add;
    protected FoodListAdapter food_adapter;

    protected FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listview, form
        lst_food = this.findViewById(R.id.lst_foods);
        et_title = this.findViewById(R.id.et_title);
        et_detail = this.findViewById(R.id.et_details);
        btn_add = this.findViewById(R.id.btn_add);

        //button
        btn_add.setOnClickListener(this);

        //inisiasi adapter
        this.food_adapter = new FoodListAdapter(this);
        //set adapter
        this.lst_food.setAdapter(food_adapter);
    }

    @Override
    public void onClick(View view) {
        if(view == btn_add){
            //Ambil data dari edit text
            String judul = et_title.getText().toString();
            String detail = et_detail.getText().toString();

            //objek food dimasukan ke adapter
            Food newFood = new Food(judul, detail);
            this.food_adapter.addLine(newFood);
            et_title.setText("");
            et_detail.setText("");
        }
    }
}
