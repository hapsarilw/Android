package com.example.tubes01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class MainActivity extends AppCompatActivity {
    ListView calculatorList;
    CalculatorAdapter calculatorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fragment);

        this.calculatorList=this.findViewById(R.id.list_operation);
        this.calculatorAdapter=new CalculatorAdapter(this);
        this.calculatorList.setAdapter(calculatorAdapter);
    }
}
