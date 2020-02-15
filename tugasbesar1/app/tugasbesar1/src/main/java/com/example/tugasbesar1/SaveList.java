package com.example.tugasbesar1;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveList {
    protected SharedPreferences sharedPreferences;
    protected final static String NAMA_SHARED_PREF="sp_nilai_display";
    protected final static String KEY_OPERATOR="OPERATOR";
    protected final static String KEY_OPERAND="OPERAND";
    protected final static String KEY_PREVIEW_RESULT="PREVIEW_RESULT";

    CalculatorFragment cf;

    public SaveList(Context context){
        this.sharedPreferences = context.getSharedPreferences(NAMA_SHARED_PREF, 0);
    }

    public void saveOperator(String operator){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_OPERATOR, operator);
        editor.apply();

    }

    public void saveOperand(String operand){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_OPERAND, operand);
        editor.apply();
    }

    public void savePreviewResult(String result){
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(KEY_PREVIEW_RESULT, cf.count());
        editor.apply();
    }

    public String getOperator(){
        return sharedPreferences.getString(KEY_OPERATOR,"");
    }

    public int getOperand(){
        return sharedPreferences.getInt(KEY_OPERAND,0);
    }

    public String getCountPreview(){
        return sharedPreferences.getString(KEY_PREVIEW_RESULT,"");
    }

}
