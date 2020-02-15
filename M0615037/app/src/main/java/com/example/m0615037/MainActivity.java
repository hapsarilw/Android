package com.example.m0615037;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    protected Button btnStart, btnStop;
    protected ImageView ivCanvas;
    protected Boolean canvasInitiated;
    protected int mColorStroke, mColorBackground;
    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected Paint strokePaint;

    protected int cx = new Random().nextInt(21)-10;
    protected int cy = new Random().nextInt(21)-10;

    protected float x,y;

    protected Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ivCanvas = this.findViewById(R.id.iv_canvas);
        this.btnStart = this.findViewById(R.id.btn_start);
        this.btnStop = this.findViewById(R.id.btn_stop);

        this.ivCanvas.setOnClickListener(this);
        this.btnStop.setOnClickListener(this);
        this.btnStart.setOnClickListener(this);

        this.mColorBackground = ResourcesCompat.getColor(getResources(), R.color.Black, null);
        this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);

        this.strokePaint = new Paint();
        this.strokePaint.setColor(mColorStroke);

        canvasInitiated = false;

        thread = new Thread(){
            public void run(){
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            update();
                            repaint();
                        }
                    });

                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        if(view == btnStart){
            if(!canvasInitiated){
                initiateCanvas();

                this.x = ivCanvas.getWidth() / 2;
                this.y = ivCanvas.getHeight() / 2;

                mCanvas.drawCircle(this.x, this.y, 50, strokePaint);

                this.canvasInitiated = true;
            }
            thread.start();
        }
        if(view == btnStop){
            thread.interrupt();
        }
    }

    public void initiateCanvas() {
        // 1. Create Bitmap
        this.mBitmap = Bitmap.createBitmap(ivCanvas.getWidth(), ivCanvas.getHeight(), Bitmap.Config.ARGB_8888);

        // 2. Associate the bitmap to the ImageView.
        this.ivCanvas.setImageBitmap(mBitmap);

        // 3. Create a Canvas with the bitmap.
        this.mCanvas = new Canvas(mBitmap);

        // new paint for stroke + style (Paint.Style.STROKE)

        //resetCanvas
        resetCanvas();
    }

    public void resetCanvas() {
        // 4. Draw canvas background
        this.mCanvas.drawColor(mColorBackground);

        // 5. force draw
        this.ivCanvas.invalidate();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void update(){
        this.x = this.x + this.cx;
        this.y = this.y + this.cy;
    }

    public void repaint(){
        if(this.x + 50 > ivCanvas.getWidth() || this.y + 50 > ivCanvas.getHeight()
        || this.x - 50 < 0 || this.y - 50 < 0){
            this.mCanvas.drawColor(mColorBackground);
        }
        else{
            this.mCanvas.drawColor(mColorBackground);
            mCanvas.drawCircle(this.x, this.y, 50, strokePaint);
            ivCanvas.invalidate();
        }
    }
}
