package com.example.t0215037;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends BaseAdapter {
    private List<Food> listFood;
    private Activity activity;

    public FoodListAdapter(Activity activity){
        this.activity = activity;
        this.listFood = new ArrayList<Food>();

    }
    public void addLine(Food newFood){
        this.listFood.add(newFood);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return listFood.size();
    }

    @Override
    public Object getItem(int i) {
        return listFood.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        //inflate layout for each list row
        convertView = LayoutInflater.from(this.activity).inflate(R.layout.food_list_item, parent, false);
        final Food currentFood = (Food)this.getItem(i);

        //1.title
        TextView tvTitle = convertView.findViewById(R.id.tv_nama);
        tvTitle.setText(currentFood.getTitle());
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFood.getFavorite()){
                    currentFood.setFavorite();
                }
                else{
                    currentFood.setNotFavorite();
                }
                notifyDataSetChanged();
            }
        });

        //2.detail (same as above)
        TextView tvDetails = convertView.findViewById(R.id.tv_details);
        tvDetails.setText(currentFood.getDetails());
        tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFood.getFavorite()){
                    currentFood.setFavorite();
                }
                else{
                    currentFood.setNotFavorite();
                }
                notifyDataSetChanged();
            }
        });

        //favorite if true ->btn_star_big _on, else btn_start_big_off
        ImageView start_button = convertView.findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFood.getFavorite()){
                    currentFood.setFavorite();
                }
                else {
                    currentFood.setNotFavorite();
                }
                notifyDataSetChanged();
            }
        });
        if(currentFood.getFavorite()){
            start_button.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{
            start_button.setImageResource(android.R.drawable.btn_star_big_off);
        }

        ImageButton trash = convertView.findViewById(R.id.trash_button);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listFood.remove(currentFood);
                notifyDataSetChanged();
            }
        });


        //return the view for the current row
        return convertView;

    }
}

class Food{
    private String title;
    private String details;
    private boolean isString;

    public Food(String title, String detail){
        this.title=title;
        this.details=detail;
        this.isString=false;
    }

    public String getDetails() {
        return details;
    }

    public String getTitle() {
        return title;
    }

    public boolean getFavorite(){
        return isString;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFavorite() {
        this.isString=true;
    }

    public void setNotFavorite(){this.isString=false;}
}
