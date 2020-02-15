package com.example.testmangaden.Fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.testmangaden.Common.Common;
import com.example.testmangaden.Detail_Comic.Detail_Comic;
import com.example.testmangaden.Interface.GetNoticeDataService;
import com.example.testmangaden.R;

import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class Comic_Fragment extends Fragment   {

    View view;
    ImageView avatar;
    TextView detail_name,date,categories,author,description;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    GetNoticeDataService service;
    Detail_Comic detail_comic;

    public Comic_Fragment() {
    }

    @SuppressLint("ValidFragment")
    public Comic_Fragment(Detail_Comic comic) {
       if(comic == null)
       {
           Log.d("TESTFAIL","");
       }
       else
       {
           this.detail_comic=comic;
       }

    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.detail_comic,container,false);
        detail_name=view.findViewById(R.id.name_avatar);
        date=view.findViewById(R.id.avatar_date);
        author=view.findViewById(R.id.avatar_author);
        categories=view.findViewById(R.id.avatar_categories);
        description=view.findViewById(R.id.description);
        avatar=view.findViewById(R.id.avatar_comic);
        detail_name.setText(Common.selectedManga.getT());

        if(Common.selectedComic !=null)
        {
            Log.d("TEST_FRAGMENT",Common.selectedComic.getAuthor());
            Log.d("TEST_FRAGMENT", String.valueOf(Common.selectedComic.getDate()));
            String dates= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Common.selectedComic.getDate() * 1000L));
            date.setText(dates);
            author.setText(Common.selectedComic.getAuthor());
            categories.setText("");
            List<String> data = Common.selectedComic.getCate();
            for(String cate: data)
            {
                categories.append(cate+" || ");
            }
            description.setText("");
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N)
            {
                description.setText((Html.fromHtml(String.valueOf(Html.fromHtml(Common.selectedComic.getDescription(),Html.FROM_HTML_MODE_LEGACY)))));
            }
            else
            {
                description.setText(Html.fromHtml(Common.selectedComic.getDescription()));
            }

            description.setMovementMethod(new ScrollingMovementMethod());
            Glide.with(view).load(Common.selectedManga.getIm()).into(avatar);

        }
        else
        {
            Log.d("TEST_FRAGMENT","FAILURE");
        }
        return  view;
}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        service= Common.getAPI();

    }


}
