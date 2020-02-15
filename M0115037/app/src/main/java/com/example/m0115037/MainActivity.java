package com.example.m0115037;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btnAction;
    protected EditText etEdit;
    protected TextView tvShowResult;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnAction = this.findViewById(R.id.btn_action);
        this.etEdit = this.findViewById(R.id.et_edit);
        this.tvShowResult = this.findViewById(R.id.tv_show_result);
        this.btnAction.setOnClickListener(this);    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.btnAction.getId()){
            this.changeText();
        }
        //mengeluarkan log entri pada method onClick yang telah anda buat untuk
        //mengetahui id dari tombol yang diklik
        Log.d("debug", "clicked");
    }

    public void changeText(){
        this.tvShowResult.setText("Text \n:" + this.etEdit.getText());
    }

}
