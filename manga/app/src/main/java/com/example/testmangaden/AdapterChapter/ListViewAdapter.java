package com.example.testmangaden.AdapterChapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testmangaden.Detail_Comic.Chap;
import com.example.testmangaden.R;

import java.util.ArrayList;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    Context context;
    int ps;
    ArrayList<Chap> url = new ArrayList<>();
    static  ItemSelector listener;
    public ListViewAdapter(ArrayList<Chap> image, Context ctx){
        context=ctx;
        url=image;
        ps=url.size()-1;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_bottom_row,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setText(position+1+"");
    }

    @Override
    public int getItemCount() {
        return url.size();
    }
    public static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(TextView)itemView.findViewById(R.id.preview);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnSelect(getAdapterPosition());
                    Log.d("Tag",getAdapterPosition()+"s");
                }
            });
        }
    }
    public void setItemSelectListener(ItemSelector selectListener){
        listener=selectListener;
    }
    public interface ItemSelector{
        public void OnSelect(int position);
    }

}
