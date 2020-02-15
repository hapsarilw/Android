package com.example.t0415037;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    protected Button btnReset, btnHitam, btnMerah, btnHijau, btnBiru;
    protected ImageView ivCanvas;
    protected GestureDetector mDetector;
    private int mColorStroke, mColorBackground;
    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected PointF start;
    protected Boolean canvasInitiated;
    protected Paint strokePaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById
        this.ivCanvas = this.findViewById(R.id.iv_canvas);
        this.btnReset = this.findViewById(R.id.btn_reset);
        this.btnHitam = this.findViewById(R.id.btn_hitam);
        this.btnMerah = this.findViewById(R.id.btn_merah);
        this.btnHijau = this.findViewById(R.id.btn_hijau);
        this.btnBiru = this.findViewById(R.id.btn_biru);

        //create gesture detector + listener
        this.ivCanvas.setOnTouchListener(this);
        this.btnReset.setOnClickListener(this);
        this.btnHitam.setOnClickListener(this);
        this.btnMerah.setOnClickListener(this);
        this.btnHijau.setOnClickListener(this);
        this.btnBiru.setOnClickListener(this);


        this.mDetector = new GestureDetector(this, new MyCustomGestureListener());


        //atribut initialization
        this.mColorBackground = ResourcesCompat.getColor(getResources(), R.color.white, null);
        this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.black, null);

        this.strokePaint = new Paint();
        this.strokePaint.setStrokeWidth(3);
        this.strokePaint.setStyle(Paint.Style.STROKE);

        this.start = new PointF();

        canvasInitiated = false;
    }

    @Override
    public void onClick(View v) {
        //btn new : initiate canvas , change to reset button
        if (v == btnReset) {
            if (!canvasInitiated) {
                initiateCanvas();
                btnReset.setText("Reset");
            }
            //btn reset : resetcanvas
            else {
                this.strokePaint.setStrokeWidth(3);
                resetCanvas();
            }
        }

        //color 1-4 : change color
        if (v == btnHitam) {
            changeStrokeColor(1);
        }
        if (v == btnMerah) {
            changeStrokeColor(2);
        }
        if (v == btnHijau) {
            changeStrokeColor(3);
        }
        if (v == btnBiru) {
            changeStrokeColor(4);
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //gesture listener
        return mDetector.onTouchEvent(event);
    }

    public void initiateCanvas() {
        // 1. Create Bitmap
        this.mBitmap = Bitmap.createBitmap(ivCanvas.getWidth(), ivCanvas.getHeight(), Bitmap.Config.ARGB_8888);

        // 2. Associate the bitmap to the ImageView.
        this.ivCanvas.setImageBitmap(mBitmap);

        // 3. Create a Canvas with the bitmap.
        this.mCanvas = new Canvas(mBitmap);

        // new paint for stroke + style (Paint.Style.STROKE)
        this.strokePaint.setColor(mColorStroke);
        this.strokePaint.setStrokeWidth(3);
        this.strokePaint.setMaskFilter(null);

        //resetCanvas
        resetCanvas();
    }

    public void resetCanvas() {
        // 4. Draw canvas background
        this.mCanvas.drawColor(mColorBackground);

        // 5. force draw
        this.ivCanvas.invalidate();

        // 6. reset stroke width + color
        this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.black, null);
        this.strokePaint.setColor(mColorStroke);

    }

    private void changeStrokeColor(int color) {
        //change stroke color using parameter (color resource id)
        if (color == 1) {
            this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.black, null);
            this.strokePaint.setColor(mColorStroke);
        }
        if (color == 2) {
            this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.red, null);
            this.strokePaint.setColor(mColorStroke);
        }
        if (color == 3) {
            this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.green, null);
            this.strokePaint.setColor(mColorStroke);
        }
        if (color == 4) {
            this.mColorStroke = ResourcesCompat.getColor(getResources(), R.color.blue, null);
            this.strokePaint.setColor(mColorStroke);
        }

    }

    private class MyCustomGestureListener extends GestureDetector.SimpleOnGestureListener {

        Path strokePath;

        @Override
        public boolean onDown(MotionEvent e) {
            //new start point with position if null, else set start position
            float x = e.getX();
            float y = e.getY();

            strokePath = new Path();

            start.set(x, y);
            strokePath.moveTo(start.x, start.y);

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //set path


            //change start point for next path
            strokePath.lineTo(e2.getX(), e2.getY());
            strokePath.moveTo(e2.getX(), e2.getY());
            strokePath.close();

            //draw path
            mCanvas.drawPath(strokePath, strokePaint);

            //redraw
            ivCanvas.invalidate();

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            //toggle change stroke + show toast

            if (strokePaint.getStrokeWidth() == 3) {
                strokePaint.setStrokeWidth(20);
                Toast toast = Toast.makeText(getApplicationContext(), "Stroke = 20", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                strokePaint.setStrokeWidth(3);
                Toast toast = Toast.makeText(getApplicationContext(), "Stroke = 3", Toast.LENGTH_SHORT);
                toast.show();
            }


        }
    }

}
