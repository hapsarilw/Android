package com.example.uts1;

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
    private List<Food> foodList;
    private Activity activity;

    public FoodListAdapter(Activity activity){
        this.activity = activity;
        this.foodList = new ArrayList<>();
    }

    public void addLine(Food food){
        this.foodList.add(food);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        //inflate layout for each list row
        convertView = LayoutInflater.from(this.activity).inflate(R.layout.food_list_item,parent, false);
        final Food currentFood = (Food)this.getItem(i);

        //1.Title
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
                //refresh adapter
                notifyDataSetChanged();
            }
        });

        //2.detail
        TextView tvDetails = convertView.findViewById(R.id.tv_details);
        tvDetails.setText(currentFood.getDetails());
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFood.getFavorite()){
                    currentFood.setFavorite();
                }
                else{
                    currentFood.setNotFavorite();
                }
                //refresh adapter
                notifyDataSetChanged();
            }
        });

        ImageView startButton = convertView.findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!currentFood.getFavorite()){
                    currentFood.setFavorite();
                }
                else{
                    currentFood.setNotFavorite();
                }
                //refresh adapter
                notifyDataSetChanged();
            }
        });

        if(currentFood.getFavorite()){
            startButton.setImageResource(android.R.drawable.btn_star_big_on);
        }
        else{
            startButton.setImageResource(android.R.drawable.btn_star_big_off);
        }

        ImageButton trash = convertView.findViewById(R.id.trash_button);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodList.remove(currentFood);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public boolean getFavorite(){
        return isString;
    }

    public void setFavorite() {
        isString = true;
    }

    public void setNotFavorite() {
        isString = false;
    }
}
