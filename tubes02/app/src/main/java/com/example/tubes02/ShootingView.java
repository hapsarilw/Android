package com.example.tubes02;


import android.content.Context;


public class ShootingView implements Runnable{
    //1. Update waktu main dan buat thread
    private boolean playing;
    private static Thread shootThread = null;


    //2. Gambar player ke ShootingView

    //tambah kelas model1 untuk digambar
    private Model1 model1;



    public ShootingView(Context context){
        super();

        this.model1 = new Model1(context);
        runGame();

    }

    private void runGame(){
        new Thread(){
            @Override
            public void run() {
                while(playing){
                    //update koordinate objek
                    update();

                    //gambar karakter di canvas
                    draw();

                    //kontrol frame saat di draw
                    control();
                }
            }
        };

    }


    private void update() {
        model1.update();
    }

    private void draw() {
        model1.getBitmap();
    }

    private void control() {
        try {
            shootThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        //ketika game berhenti
        playing = false;
        try{
            //hentikan thread
            shootThread.join();
        }catch (InterruptedException e){

        }
    }

    public void resume(){
        playing = true;
        shootThread = new Thread(this);
        shootThread.start();
    }


    @Override
    public void run() {

    }
}
