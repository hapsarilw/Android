package com.example.m1015037;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.m1015037.MainActivity.ACTION_CUSTOM_BROADCAST;

public class CustomReceiver extends BroadcastReceiver {
    MainActivity ui;

    public CustomReceiver(MainActivity act){
        this.ui = act;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ui = new MainActivity();
        String intentAction = intent.getAction();
        if(intentAction != null){
            String toastMessage = "unknown intent action";
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    int param = intent.getIntExtra("param", 0);
                    toastMessage = "Custom Broadcasr Received" + param;
                    break;

            }
            this.ui.setTvResult(toastMessage);
        }
    }
}
