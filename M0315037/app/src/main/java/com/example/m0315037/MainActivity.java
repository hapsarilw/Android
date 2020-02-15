package com.example.m0315037;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentListener{

    private FirstFragment fragment1;
    private SecondFragment fragment2;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2. Inisiasi Fragment di aktifitas
        this.fragment1 = FirstFragment.newInstance("New Fragment 1");
        this.fragment2 = SecondFragment.newInstance("New Fragment 2");

        //3. Inisiasi FragmentManager
        this.fragmentManager = this.getSupportFragmentManager();

        //4. Menggunakan Fragment transaction
        FragmentTransaction ft = this.fragmentManager.beginTransaction();

        //menyimpan sementara fragment pada back stack
        //yang akan dikeluarkan jika kita menekan tombol back.
        ft.add(R.id.fragment_container, this.fragment1)
                .commit();
    }
    public void changePage(int page){
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(page == 1){
            if(this.fragment1.isAdded()){
                ft.show(this.fragment1);
            }
            else{
                ft.add(R.id.fragment_container, this.fragment1);
            }
            if(this.fragment2.isAdded()){
                ft.hide(this.fragment2);
            }
        }
        else if (page == 2){
            if(this.fragment2.isAdded()){
                ft.show(this.fragment2);
            }
            else{
                ft.add(R.id.fragment_container, this.fragment2)
                .addToBackStack(null);
            }
            if(this.fragment1.isAdded()){
                ft.hide(this.fragment1);
            }
        }
        ft.commit();
    }
    @Override
    public void changeMessage(String message){
        String input_message = this.fragment2.et_message.getText()
                .toString();
        fragment1.tvMesaage.setText(input_message);
    }
}

