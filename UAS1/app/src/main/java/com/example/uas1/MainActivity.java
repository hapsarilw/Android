package com.example.uas1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    EditText inputName;
    EditText inputEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        Button btnNextScreen = findViewById(R.id.btnNextScreen);

        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buat intent baru
                Intent nextScreen = new Intent(getApplicationContext(), MainActivity2.class);

                //kirim data ke activity lain
                nextScreen.putExtra("name", inputName.getText().toString());
                nextScreen.putExtra("email", inputEmail.getText().toString());

                Log.e("n", inputName.getText()+"."+inputEmail.getText());

                startActivity(nextScreen);
            }
        });
    }

}
