package com.example.testmangaden.AsynscTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.testmangaden.Common.Common;
import com.example.testmangaden.Detail_Comic.Detail_Comic;
import com.example.testmangaden.Interface.GetNoticeDataService;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class AsysncTask extends AsyncTask<String, Detail_Comic,Detail_Comic> {
    private CompositeDisposable compositeDisposable= new CompositeDisposable();
    GetNoticeDataService service;
    private Detail_Comic result;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        service= Common.getAPI();
    }

    @Override
    protected void onPostExecute(Detail_Comic comic) {
        super.onPostExecute(comic);
        Common.selectedComic=comic;

    }

    @Override
    protected Detail_Comic doInBackground(String... strings) {
        Log.d("AAAAA",strings[0]);
        final Detail_Comic[] comics = new Detail_Comic[1];
        compositeDisposable.add(service.getDetailComic(strings[0]).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Detail_Comic>() {
                    @Override
                    public void accept(Detail_Comic comic) throws Exception {
                        comics[0] = comic;
                        result=comic;
                        Common.selectedComic=comic;
                        if(comic!=null)
                        {
                            Log.d("GOGOGO", comic.getDescription());
                            Common.selectedComic=comic;
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        Log.d("GOGOGO", "ERROR");

                    }
                }));
        return result;
    }


    public Detail_Comic getcomic()
    {
        return  result;
    }

}
