package com.example.m0415037;

import android.content.SharedPreferences;
import android.content.Context;

public class PenyimpananNilaiDIsplay {
    protected SharedPreferences sharedPref;
    protected final static String NAMA_SHARED_PREF="sp_nilai_display";
    protected final static String KEY_BARANG="BARANG";
    protected final static String KEY_HARGA="HARGA";
    protected final static String KEY_KETERANGAN="KETERANGAN";

    public PenyimpananNilaiDIsplay(Context context){
        this.sharedPref = context.getSharedPreferences(NAMA_SHARED_PREF,0);
    }

    public void saveBarang(String barang){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(KEY_BARANG, barang);
        editor.apply();
    }

    public void saveHarga(String harga){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(KEY_HARGA, harga);
        editor.apply();
    }

    public void saveKeterangan(String keterangan){
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString(KEY_KETERANGAN, keterangan);
        editor.apply();
    }

    public String getBarang(){
        return sharedPref.getString(KEY_BARANG, "");
    }

    public String getHarga(){
        return sharedPref.getString(KEY_HARGA, "");
    }

    public String getKeterangan(){
        return sharedPref.getString(KEY_KETERANGAN, "");
    }



}

