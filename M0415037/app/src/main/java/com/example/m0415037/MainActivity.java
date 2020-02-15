package com.example.m0415037;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    protected PenyimpananNilaiDIsplay pencatat;
    protected EditText etBarang, etHarga, etKeterangan;
    protected Button add;
    static final int READ_BLOCK_SIZE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.etBarang = this.findViewById(R.id.et_barang);
        this.etHarga = this.findViewById(R.id.et_harga);
        this.etKeterangan = this.findViewById(R.id.et_keterangan);
        this.pencatat = new PenyimpananNilaiDIsplay(this);
        this.add= this.findViewById(R.id.btn_page);    }

    //        Ketika aplikasi ini ditutup/tidak lagi ditampilkan (pause) oleh smartphone, aplikasi ini akan memerintahkan objek
    //        PenyimpanNilaiDisplay untuk menyimpan String yang sudah diketikkan pada EditText Barang.
    @Override
    protected void onPause(){
        super.onPause();
        this.pencatat.saveBarang(this.etBarang.getText().toString());
        this.pencatat.saveHarga(this.etHarga.getText().toString());
        this.pencatat.saveKeterangan(this.etKeterangan.getText().toString());

    }
    //        Ketika aplikasi anda ditampilkan (resume), aplikasi akan meminta nilai yang tersimpan pada
    //        PenyimpanNilaiDisplay dan menampilkannya ke EditTextBarang.
    @Override
    protected void onResume(){
        super.onResume();
        this.etBarang.setText(this.pencatat.getBarang());
        this.etHarga.setText(this.pencatat.getHarga());
        this.etKeterangan.setText(this.pencatat.getKeterangan());
    }

    //write files
    public void WriteBtn(View v){
        try{
            FileOutputStream fileout = openFileOutput("myfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(this.etBarang.getText().toString() + " ");
            outputWriter.write(this.etHarga.getText().toString()+ " ");
            outputWriter.write(this.etKeterangan.getText().toString() + " ");
            outputWriter.close();

            //tampilkan pesan data sudah disimoan
            Toast.makeText(getBaseContext(), "File sukses tersimpan", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

