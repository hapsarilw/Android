package com.example.t0115037;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected Button btn1, btn2, btn3, btn4;
    protected TextView showResult;
    protected Button resetBtn, plusBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        showResult = this.findViewById(R.id.showNumber);

        btn1 = this.findViewById(R.id.button1);
        this.btn1.setOnClickListener(this);

        btn2 = this.findViewById(R.id.button2);
        this.btn2.setOnClickListener(this);

        btn3 = this.findViewById(R.id.button3);
        this.btn3.setOnClickListener(this);

        btn4 = this.findViewById(R.id.button4);
        this.btn4.setOnClickListener(this);

        resetBtn = this.findViewById(R.id.reset_button);
        this.resetBtn.setOnClickListener(this);

        plusBtn = this.findViewById(R.id.plus_button);
        this.plusBtn.setOnClickListener(this);
        plusBtn.setText("+");

    }

    @Override
    public void onClick(View view){
        String text = showResult.getText().toString();
        int temp = Integer.parseInt(text);

        //Ubah + jadi reset, vice versa
        if(view == plusBtn){
            if(plusBtn.getText() == "+"){
                plusBtn.setText("-");
            }
            else{
                plusBtn.setText("+");
            }
        }

        if(view == btn1){
            if(plusBtn.getText() == "+"){
                addOperate(temp, 1);
            }
            else{
                minOperate(temp, 1);
            }
        }

        if(view == btn2){
            if(plusBtn.getText() == "+"){
                addOperate(temp, 2);
            }
            else{
                minOperate(temp, 2);
            }
        }

        if(view == btn3){
            if(plusBtn.getText() == "+"){
                addOperate(temp, 3);
            }
            else{
                minOperate(temp, 3);
            }
        }

        if(view == btn4){
            if(plusBtn.getText() == "+"){
                addOperate(temp, 4);
            }
            else{
                minOperate(temp, 4);
            }
        }

        if (view == resetBtn){
            showResult.setText("0");
        }

    }

    public void minOperate(int res, int n){
        int result = res -n;
        String hasil = result+ "";
        showResult.setText(hasil);
    }

    public void addOperate(int res, int n){
        int result = res +n;
        String hasil = result+ "";
        showResult.setText(hasil);
    }

}
