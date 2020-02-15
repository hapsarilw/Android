package com.example.tubes2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public class Peluru {

    private Bitmap mBitmap;
    private int mX;
    private int mY;
    private Rect mCollision;
    private int mScreenSizeX;
    private int mScreenSizeY;
    private boolean mIsEnemy;

    public Peluru(Context context, int screenSizeX, int screenSizeY, int spaceShipX, int spaceShipY, Bitmap spaceShip, boolean isEnemy){
        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mIsEnemy = isEnemy;

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.peluru);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, mBitmap.getWidth() * 3/5, mBitmap.getHeight() * 3/5, false);

        mX = spaceShipX + spaceShip.getWidth()/2 - mBitmap.getWidth()/2;
        if (mIsEnemy){
            mY = spaceShipY + mBitmap.getHeight() + 10;
        }else{
            mY = spaceShipY - mBitmap.getHeight() - 10;
        }

        mCollision = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void update(){
        if (mIsEnemy){
            mY += mBitmap.getHeight() + 10;
            mCollision.left = mX;
            mCollision.top = mY;
            mCollision.right = mX + mBitmap.getWidth();
            mCollision.bottom = mY + mBitmap.getHeight();
        }else{
            mY -= mBitmap.getHeight() - 10;
            mCollision.left = mX;
            mCollision.top = mY;
            mCollision.right = mX + mBitmap.getWidth();
            mCollision.bottom = mY + mBitmap.getHeight();
        }

    }

    public boolean isEnemy() {
        return mIsEnemy;
    }

    public Rect getCollision() {
        return mCollision;
    }

    public void destroy(){
        if (mIsEnemy){
            mY = mScreenSizeY;
        }else{
            mY = 0 - mBitmap.getHeight();
        }

    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
}
