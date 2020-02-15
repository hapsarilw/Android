package com.example.tugasbesar1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.view.Menu;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


import android.content.SharedPreferences;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


public class MainActivity extends AppCompatActivity implements FragmentListener{
    FragmentManager fragmentManager;

    CalculatorFragment calculatorFragment;
    AddFragment addFragment;

//    DrawerLayout drawerLayout;

//    SaveList saveList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.calculatorFragment = new CalculatorFragment();
        this.addFragment = new AddFragment();

//        this.drawerLayout = findViewById(R.id.drawer_layout);

        this.fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fragment_container, this.calculatorFragment).addToBackStack(null).commit();

        this.changePage(1);

//        this.saveList = new SaveList(this);
    }

    @Override
    public void changePage(int page) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        if(page == 1){
            if(this.calculatorFragment.isAdded()){
                ft.show(this.calculatorFragment);
            }else{
                ft.add(R.id.fragment_container, this.calculatorFragment);
            }
            if(this.addFragment.isAdded()){
                ft.hide(this.addFragment);
            }
        }else if(page == 2){
            if(this.addFragment.isAdded()){
                ft.show(this.addFragment);
            }else {
                ft.add(R.id.fragment_container, this.addFragment);
            }
            if(this.calculatorFragment.isAdded()){
                ft.hide(this.calculatorFragment);
            }
        }
        ft.commit();
    }

    @Override
    public void clearList() {
        this.calculatorFragment.itemList.clear();
        this.calculatorFragment.adapter.notifyDataSetChanged();
    }

    @Override
    public void closeApplication() {
//        this.drawerLayout.closeDrawers();
    }

    @Override
    public void addList(String number) {
        FragmentTransaction ft = this.fragmentManager.beginTransaction();
        this.calculatorFragment.itemList.add(new Item(number));
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.addFragment.etOperand.getWindowToken(), 0);
        ft.commit();
    }

//    @Override
//    public void onPause(){
//        super.onPause();
//        this.saveList.saveOperator(this.addFragment.etOperand.getText().toString());
//        this.saveList.saveOperand(this.addFragment.spinner);
//
//    }
//    //        Ketika aplikasi anda ditampilkan (resume), aplikasi akan meminta nilai yang tersimpan pada
//    //        PenyimpanNilaiDisplay dan menampilkannya ke EditTextBarang.
//    @Override
//    public void onResume(){
//        super.onResume();
//        this.addFragment.etOperand.setText(this.saveList.getOperator());
//        this.addFragment.spinner.setSelection(this.saveList.getOperand());
//
//    }
//
//    //write files
//    public void WriteBtn(View v){
//        try{
//            FileOutputStream fileout = openFileOutput("myCalculator.txt", MODE_PRIVATE);
//            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
//            outputWriter.write(this.addFragment.etOperand.getText().toString() + " ");
//            outputWriter.write(this.addFragment.spinner.getSelectedItemPosition());
//            outputWriter.close();
//
//            //tampilkan pesan data sudah disimoan
//            Toast.makeText(getBaseContext(), "File sukses tersimpan", Toast.LENGTH_SHORT).show();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}
