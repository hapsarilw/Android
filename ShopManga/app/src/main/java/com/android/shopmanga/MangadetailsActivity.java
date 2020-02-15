package com.android.shopmanga;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MangadetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TelephonyManager telephonyManager; // pour recuperer l'etat de l'application 'telephone'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mangadetails);

        Manga manga = (Manga) getIntent().getSerializableExtra("manga");

        TextView mangaName = findViewById(R.id.mangaName);
        TextView price = findViewById(R.id.price);
        TextView sellerName = findViewById(R.id.sellerName);
        TextView address = findViewById(R.id.address);
        TextView telephone = findViewById(R.id.telephone);
        TextView volume = findViewById(R.id.Volume);
        image = findViewById(R.id.imageView);
        LoadImageFromUrl("https://cdn.mangaeden.com/mangasimg/"+ manga.getImageUrl());

        mangaName.setText( "Manga name :  "+manga.getMangaName());
        price.setText(     "Price :       "+Double.toString(manga.getPrice()));
        sellerName.setText("Seller Name : "+manga.getSellerName());
        address.setText(   "Addresse :    "+manga.getAddress());
        telephone.setText( "Telephone :   "+manga.getTelephone());
        volume.setText(     "Volume :"+manga.getVolume() );

        // ici on gere le bouton call
        ImageView callButton = findViewById(R.id.call_button);
        telephonyManager = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
        callButton.setOnClickListener(v -> {
            if(telephonyManager.getCallState() == TelephonyManager.CALL_STATE_IDLE) {
                Intent appel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + manga.getTelephone()));
                startActivity(appel);
            }
        });
    }
    private void LoadImageFromUrl(String url){
        Picasso.with(this).load(url)
                .error(R.mipmap.ic_launcher)
                .resize(720,720)
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
