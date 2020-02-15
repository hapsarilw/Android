package com.example.t0515037;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class CounterAsyncTask extends AsyncTask<Integer, Integer, String> {

    protected int countNumber;
    protected int pos;
    protected CounterListAdapter counterListAdapter;

    protected void onPreExecute() {
        this.countNumber = 0;
        //this.pos = (int) counterListAdapter.getItem(0);
    }

    @Override
    protected String doInBackground(Integer... integers) {
        int max = integers[0];

        for (int i = 0; i < max; i++) {
            countNumber = i;
            this.publishProgress(countNumber);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

        return "finished";
    }

    protected void onPostExecute(String result) {
        Log.d("counter" + this.pos, result);
    }

    protected void onProgressUpdate(Integer... progress) {
        int count = progress[0];

//        counterListAdapter.updateLine(this.pos, "" + count);
    }

}
