package com.example.tubes2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;


public class Player {

    protected Bitmap mBitmap, mBitmap1, mBitmap2;

    private int mX;
    private int mY;
    private int mSpeed;
    private int mMaxX;
    private int mMinX;
    private int mMaxY;
    private int mMinY;
    private int mMargin = 16;
    private boolean mIsSteerLeft, mIsSteerRight;
    private float mSteerSpeed;
    private Rect mCollision;
    private ArrayList<Peluru> mPelurus;
    private Context mContext;
    private int mScreenSizeX, mScreenSizeY;

    public Player(Context context, int screenSizeX, int screenSizeY) {
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mContext = context;

        mSpeed = 1;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3/5, mBitmap.getHeight() * 3/5, false);
        mBitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_left);
        mBitmap1 = Bitmap.createScaledBitmap(mBitmap1, mBitmap1.getWidth() * 3/5, mBitmap1.getHeight() * 3/5, false);
        mBitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right);
        mBitmap2 = Bitmap.createScaledBitmap(mBitmap2, mBitmap2.getWidth() * 3/5, mBitmap2.getHeight() * 3/5, false);

        mMaxX = screenSizeX - mBitmap.getWidth();
        mMaxY = screenSizeY - mBitmap.getHeight();
        mMinX = 0;
        mMinY = 0;

        mX = screenSizeX/2 - mBitmap.getWidth()/2;
        mY = screenSizeY - mBitmap.getHeight() - mMargin;

        mPelurus = new ArrayList<>();

        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update(){
        if (mIsSteerLeft){
            mX -= 10 * mSteerSpeed;
            if (mX<mMinX){
                mX = mMinX;
            }
        }else if (mIsSteerRight){
            mX += 10 * mSteerSpeed;
            if (mX>mMaxX){
                mX = mMaxX;
            }
        }

        mCollision.left = mX;
        mCollision.top = mY;
        mCollision.right = mX + mBitmap.getWidth();
        mCollision.bottom = mY + mBitmap.getHeight();

        for (Peluru l : mPelurus) {
            l.update();
        }

        boolean deleting = true;
        while (deleting) {
            if (mPelurus.size() != 0) {
                if (mPelurus.get(0).getY() < 0) {
                    mPelurus.remove(0);
                }
            }

            if (mPelurus.size() == 0 || mPelurus.get(0).getY() >= 0) {
                deleting = false;
            }
        }
    }

    public ArrayList<Peluru> getLasers() {
        return mPelurus;
    }

    public void fire(){
        mPelurus.add(new Peluru(mContext, mScreenSizeX, mScreenSizeY, mX, mY, mBitmap, false));

    }

    public Rect getCollision() {
        return mCollision;
    }

    public void steerRight(float speed){
        mIsSteerLeft = false;
        mIsSteerRight = true;
        mSteerSpeed = Math.abs(speed);
    }

    public void steerLeft(float speed){
        mIsSteerRight = false;
        mIsSteerLeft = true;
        mSteerSpeed = Math.abs(speed);
    }

    public void stay(){
        mIsSteerLeft = false;
        mIsSteerRight = false;
        mSteerSpeed = 0;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public Bitmap getBitmap1() {
        return mBitmap1;
    }
    public Bitmap getBitmap2() {
        return mBitmap2;
    }

    public Drawable getDraw1(){
        Drawable d = new BitmapDrawable(mContext.getResources(), mBitmap1);
        return d;
    }

    public Drawable getDraw2(){
        Drawable d = new BitmapDrawable(mContext.getResources(), mBitmap2);
        return d;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }

    public int getSpeed() {
        return mSpeed;
    }
}
