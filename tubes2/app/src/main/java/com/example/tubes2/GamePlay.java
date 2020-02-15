package com.example.tubes2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class GamePlay extends SurfaceView implements Runnable, View.OnClickListener {

    private Button btnLeft, btnRight;
    private Thread mGameThread;
    private volatile boolean mIsPlaying;
    private Player mPlayer;
    private Paint mPaint;
    private Canvas mCanvas;
    private SurfaceHolder mSurfaceHolder;
    private ArrayList<Peluru> mPelurus;
    private ArrayList<Alien> mEnemies;
    private int mScreenSizeX, mScreenSizeY;
    private int mCounter = 0;
    private SharedPreferencesManager mSP;
    private WebService ws;
    public static int SCORE = 0;
    public static int METEOR_DESTROYED = 0;
    public static int ENEMY_DESTROYED = 0;
    private volatile boolean mIsGameOver;
    private volatile boolean mNewHighScore;


    public GamePlay(Context context, int screenSizeX, int screenSizeY) {
        super(context);

        mScreenSizeX = screenSizeX;
        mScreenSizeY = screenSizeY;
        mSP = new SharedPreferencesManager(context);

        mPaint = new Paint();
        mSurfaceHolder = getHolder();
        reset();
    }

    void reset() {
        SCORE = 0;
        mPlayer = new Player(getContext(), mScreenSizeX, mScreenSizeY);
        mPelurus = new ArrayList<>();
        mEnemies = new ArrayList<>();
        mIsGameOver = false;
        mNewHighScore = false;
    }

    @Override
    public void run() {
        while (mIsPlaying) {
            if (!mIsGameOver) {
                update();
                draw();
                control();
            }
        }
        Log.d("GameThread", "Run stopped");
    }

    public void update() {
        mPlayer.update();
        if (mCounter % 200 == 0) {
            mPlayer.fire();
        }



        boolean deleting = true;


        for (Alien e : mEnemies) {
            e.update();
            if (Rect.intersects(e.getCollision(), mPlayer.getCollision())) {
                e.destroy();
                mIsGameOver = true;
                if (SCORE>=mSP.getHighScore()){
                    mSP.saveHighScore(SCORE, METEOR_DESTROYED, ENEMY_DESTROYED);
                    ws.saveHighScoreWS(SCORE, ENEMY_DESTROYED);
                }
            }

            for (Peluru l : mPlayer.getLasers()) {
                if (Rect.intersects(e.getCollision(), l.getCollision())) {
                    e.hit();
                    l.destroy();
                }
            }
        }
        deleting = true;
        while (deleting) {
            if (mEnemies.size() != 0) {
                if (mEnemies.get(0).getY() > mScreenSizeY) {
                    mEnemies.remove(0);
                }
            }

            if (mEnemies.size() == 0 || mEnemies.get(0).getY() <= mScreenSizeY) {
                deleting = false;
            }
        }
        if (mCounter % 2000 == 0) {
            mEnemies.add(new Alien(getContext(), mScreenSizeX, mScreenSizeY));
        }
    }

    public void draw() {
        if (mSurfaceHolder.getSurface().isValid()) {
            mCanvas = mSurfaceHolder.lockCanvas();
            mCanvas.drawColor(Color.BLACK);
            mCanvas.drawBitmap(mPlayer.getBitmap(), mPlayer.getX(), mPlayer.getY(), mPaint);
            for (Peluru l : mPlayer.getLasers()) {
                mCanvas.drawBitmap(l.getBitmap(), l.getX(), l.getY(), mPaint);
            }
            for (Alien e : mEnemies) {
                mCanvas.drawBitmap(e.getBitmap(), e.getX(), e.getY(), mPaint);
            }
            drawArrow();
            drawScore();
            if (mIsGameOver) {
                drawGameOver();
            }

            mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }


    void drawArrow(){
        mCanvas.drawBitmap(mPlayer.getBitmap1(), 50, mScreenSizeY-150, mPaint);
        mCanvas.drawBitmap(mPlayer.getBitmap2(), mScreenSizeX-150, mScreenSizeY-150, mPaint);
    }

    void drawScore() {
        Paint score = new Paint();
        score.setTextSize(30);
        score.setColor(Color.WHITE);
        mCanvas.drawText("Skor : " + SCORE, 100, 50, score);
    }

    void drawGameOver() {
        Paint gameOver = new Paint();
        gameOver.setTextSize(100);
        gameOver.setTextAlign(Paint.Align.CENTER);
        gameOver.setColor(Color.WHITE);
        mCanvas.drawText("GAME OVER", mScreenSizeX / 2, mScreenSizeY / 2, gameOver);
        Paint highScore = new Paint();
        highScore.setTextSize(50);
        highScore.setTextAlign(Paint.Align.CENTER);
        highScore.setColor(Color.WHITE);
        if (mNewHighScore){
            mCanvas.drawText("High Score : " + mSP.getHighScore(), mScreenSizeX / 2, (mScreenSizeY / 2) + 60, highScore);
            Paint enemyDestroyed = new Paint();
            enemyDestroyed.setTextSize(50);
            enemyDestroyed.setTextAlign(Paint.Align.CENTER);
            enemyDestroyed.setColor(Color.WHITE);
            mCanvas.drawText("Alien Destroyed : " + mSP.getEnemyDestroyed(), mScreenSizeX / 2, (mScreenSizeY / 2) + 120, enemyDestroyed);
            Paint meteorDestroyed = new Paint();
            meteorDestroyed.setTextSize(50);
            meteorDestroyed.setTextAlign(Paint.Align.CENTER);
            meteorDestroyed.setColor(Color.WHITE);
            mCanvas.drawText("Meteor Destroyed : " + mSP.getMeteorDestroyed(), mScreenSizeX / 2, (mScreenSizeY / 2) + 180, meteorDestroyed);
        }

    }

    public void steerLeft(float speed) {
        mPlayer.steerLeft(speed);
    }

    public void steerRight(float speed) {
        mPlayer.steerRight(speed);
    }

    public void stay() {
        mPlayer.stay();
    }

    public void control() {
        try {
            if (mCounter == 10000) {
                mCounter = 0;
            }
            mGameThread.sleep(20);
            mCounter += 20;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        Log.d("GameThread", "Main");
        mIsPlaying = false;
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        mIsPlaying = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mIsGameOver){
                    ((Activity) getContext()).finish();
                    getContext().startActivity(new Intent(getContext(), MainMenuActivity.class));
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {

    }
}
