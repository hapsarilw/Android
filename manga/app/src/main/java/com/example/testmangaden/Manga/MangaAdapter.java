package com.example.testmangaden.Manga;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.testmangaden.Activity.detail_comic;
import com.example.testmangaden.Common.Common;
import com.example.testmangaden.ImageZoom.AlphaTextView;
import com.example.testmangaden.Interface.iRecyclerOnClick;
import com.example.testmangaden.R;

import java.util.ArrayList;
import java.util.List;


public class MangaAdapter  extends RecyclerView.Adapter<MangaAdapter.ViewHolder> implements Filterable {
    private ArrayList<Manga> list;
    private  ArrayList<Manga> source;
    Context context;

    public MangaAdapter(ArrayList<Manga> list) {
        this.list = list;
        this.source=list;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        context= viewGroup.getContext();
        View view =layoutInflater.inflate(R.layout.single_row_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Manga manga= list.get(i);
        viewHolder.txt_name.setText(manga.getT());
        Glide.with(context)
                .load(manga.getIm().trim())
                .into(viewHolder.img);

        viewHolder.setiRecyclerOnClick(new iRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selectedManga= list.get(position);
                 Intent intent =new Intent(context, detail_comic.class);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString();
                if(searchString.isEmpty())
                    list=source;
                else{
                    List<Manga> result= new ArrayList<>();
                    for(Manga manga: source)
                    {
                        if(manga.getT().toLowerCase().contains(searchString.toLowerCase()))
                        {
                            result.add(manga);
                        }
                    }
                    list= (ArrayList<Manga>) result;
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values=list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    list= (ArrayList<Manga>) results.values;
                    notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView img;
        public AlphaTextView txt_name;
        iRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(com.example.testmangaden.Interface.iRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.icon);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_name.onSetAlpha(150);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
                iRecyclerOnClick.onClick(v,getAdapterPosition());
        }
    }

}
