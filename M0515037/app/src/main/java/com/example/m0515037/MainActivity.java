package com.example.m0515037;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener, GestureDetector.OnGestureListener{
    protected ImageView ivCanvas;
    protected Button btnNew;
    protected Bitmap mBitmap, mBitmap2;
    protected Canvas mCanvas, mCanvas2;
    protected int vWidth=720, vHeight=1000;
    protected Context context = null;
    protected GestureDetector mDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById
        ivCanvas = this.findViewById(R.id.iv_canvas);
        btnNew = this.findViewById(R.id.btn_new);


        btnNew.setOnClickListener(this);
        ivCanvas.setOnClickListener(this);
        //create gesture detector + listener
        this.mDetector = new GestureDetector(this, this);


        //atribut initialization

    }

    @Override
    public void onClick(View view) {
        //btn new : initiate canvas
        if(view.getId() == this.btnNew.getId()){
            initiateCanvas();
        }
    }

    public void initiateCanvas(){
        // 1. Create Bitmap
        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);

        // 2. Associate the bitmap to the ImageView.
        ivCanvas.setImageBitmap(mBitmap);

        // 3. Create a Canvas with the bitmap.
        mCanvas = new Canvas(mBitmap);

        // new paint for stroke + style (Paint.Style.STROKE)
        Paint paint = new Paint();
        int mColorTest = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        paint.setColor(mColorTest);

        //create rectangle
        Rect rect = new Rect(10, 20, 100,100);
        mCanvas.drawRect(rect, paint);

        //create circle
        mCanvas.drawCircle(200, 500, 100, paint);


        //resetCanvas
        resetCanvas();
    }


    public void resetCanvas(){
        // 4. Draw canvas background
        int mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        mCanvas.drawColor(mColorBackground);

        // 5. force draw
        this.ivCanvas.invalidate();

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //touch listener
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                Log.d("touch_listener", "down");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d("touch_listener", "pointer_down");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("touch_listener", "up");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d("touch_listener", "pointer_up");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("touch_listener", "move");
                break;
        }
        return this.mDetector.onTouchEvent(motionEvent);
    }


    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
