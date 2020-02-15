package com.example.tugasbesar1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {
    Context context;
    List<Item> items;
    LayoutInflater layoutInflater;

    public Adapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        this.layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Object getItem(int i) {
        return this.items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            view = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        viewHolder.operator.setText(items.get(i).getOperator());
        viewHolder.operand.setText(String.valueOf(items.get(i).getNumber()));
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }
    private class ViewHolder{
//        TextView operator;
        TextView operand;
        ImageButton btnDelete;

        public ViewHolder(View view){
//            operator = view.findViewById(R.id.list_operator);
            operand = view.findViewById(R.id.list_operand);
            btnDelete = view.findViewById(R.id.btn_delete);
        }
    }
}
