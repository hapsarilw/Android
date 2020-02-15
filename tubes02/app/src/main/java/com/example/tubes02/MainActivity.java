package com.example.tubes02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnStart;
    private ShootingView shootingView;
    protected ImageView ivCanvas;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected Model1 model1;
    protected Context context;
    protected int vWidth=720, vHeight=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnStart = findViewById(R.id.btn_start);
        this.ivCanvas = findViewById(R.id.iv_canvas);

        btnStart.setOnClickListener(this);
        ivCanvas.setOnClickListener(this);

        Model1 model1;

        shootingView = new ShootingView(this);



    }

    @Override
    public void onClick(View view) {
        if(view == btnStart){
            if(btnStart.getText().toString() == "START"){
                btnStart.setText("STOP");
                btnStart.setBackgroundColor(getResources().getColor(R.color.red));
            }
            else{
                btnStart.setText("START");
                btnStart.setBackgroundColor(getResources().getColor(R.color.green));
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        shootingView.pause();
    }
//
//    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        shootingView.resume();
    }


    public void resetCanvas(){

        // 5. force draw
        this.ivCanvas.invalidate();

    }
}
