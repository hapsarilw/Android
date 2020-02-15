package com.example.m1015037;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CustomReceiver mReceiver;
    TextView tvBroadcast;
    Button btnClick;
    protected static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new CustomReceiver(MainActivity.this);


        tvBroadcast = findViewById(R.id.tv_broadcast);
        btnClick = findViewById(R.id.btn_click);

        btnClick.setOnClickListener(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(this.mReceiver, filter);

        LocalBroadcastManager.getInstance(this).registerReceiver(this.mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    @Override
    protected  void onDestroy(){
        this.unregisterReceiver(this.mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mReceiver);

        super.onDestroy();
    }

    public void setTvResult(CharSequence res){
        this.tvBroadcast.setText(res);
    }

    @Override
    public void onClick(View view) {
        //Selanjutnya ketika tombol ditekan, maka kita akan membuat intent dan mengirimkan broadcast
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);

    }
}
