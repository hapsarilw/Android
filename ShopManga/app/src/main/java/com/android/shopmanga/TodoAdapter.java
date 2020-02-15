package com.android.shopmanga;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<Manga> {
    private final int layout;
    private ImageView image;

    public TodoAdapter(@NonNull Context context, int resource, @NonNull List<Manga> objects) {
        super(context, resource, objects);
        layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null){
            // Create new view
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(layout, null);
        } else {
            view = convertView;
        }

        Manga item = getItem(position);

        // Recycle view
        TextView itemMangaName = view.findViewById(R.id.ItemMangaName);
        TextView itemMangaPrice = view.findViewById(R.id.ItemMangaPrice);
        TextView itemMangaAddress= view.findViewById(R.id.ItemMangaAddress);
        TextView itemMangaVolume = view.findViewById(R.id.ItemMangaVolume);
        image = view.findViewById(R.id.listImageView);

        if(image != null)
            LoadImageFromUrl("https://cdn.mangaeden.com/mangasimg/"+ item.getImageUrl());
        itemMangaName.setText(item.getMangaName());
        itemMangaAddress.setText(item.getAddress());
        itemMangaPrice.setText(Double.toString(item.getPrice()) + " euro");
        itemMangaVolume.setText(Double.toString(item.getVolume()));

        return view;

    }
    private void LoadImageFromUrl(String url){
        Picasso.with(getContext()).load(url)
                .error(R.mipmap.ic_launcher)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }
                    @Override
                    public void onError() {

                    }
                });
    }

}

