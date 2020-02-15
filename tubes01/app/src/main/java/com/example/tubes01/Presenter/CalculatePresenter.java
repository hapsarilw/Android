package com.example.tubes01.Presenter;

import android.view.View;

import com.example.tubes01.Model.Calculator;
import com.example.tubes01.Model.ICalculatorModel;
import com.example.tubes01.View.ICalculateView;

public class CalculatePresenter {
    Calculator calculator;
    ICalculateView gui;

    public CalculatePresenter(Calculator calculator, ICalculateView view){
        this.calculator = calculator;
        this.gui = view;
    }

    public void addOperation(){

    }

}
