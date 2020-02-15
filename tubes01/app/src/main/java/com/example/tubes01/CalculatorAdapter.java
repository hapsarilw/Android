package com.example.tubes01;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tubes01.Model.Calculator;

import java.util.ArrayList;
import java.util.List;

class CalculatorAdapter extends BaseAdapter {
    private List<Calculator> listCalculator;
    private Activity activity;

    public CalculatorAdapter(Activity activity){
        this.listCalculator = new ArrayList<Calculator>();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listCalculator.size();
    }

    @Override
    public Object getItem(int i) {
        return listCalculator.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //mengisi @layout dengan barisan list
        convertView = LayoutInflater.from(this.activity).inflate(R.layout.list_item_operation, viewGroup, false);
        final Calculator currentCalculator = (Calculator) this.getItem(i);

        //1. operand
        TextView tvOperand = convertView.findViewById(R.id.tvOperand);
        tvOperand.setText(currentCalculator.getOperand());

        //2. Operator
        TextView tvOperator = convertView.findViewById(R.id.tvOperator);
        tvOperator.setText(currentCalculator.getOperator());

        //3. Trash
        ImageButton trash = convertView.findViewById(R.id.trash_button);
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCalculator.remove(currentCalculator);
                notifyDataSetChanged();
            }
        });
        return null;
    }
}
