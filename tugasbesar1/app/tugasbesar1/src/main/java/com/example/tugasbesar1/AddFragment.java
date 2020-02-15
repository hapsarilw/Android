package com.example.tugasbesar1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener {
    Spinner spinner;
    EditText etOperand;
    TextView tvPreview_calculation;
    Button btnSubmit;
    Intent intent;
    CalculatorFragment cf;
    int spinnerPosition;
    SaveList saveList;
    String operand;


    FragmentListener fragmentListener;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        this.spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

        this.etOperand = view.findViewById(R.id.et_operand);
        this.tvPreview_calculation = view.findViewById(R.id.list_preview_cal);

        this.btnSubmit = view.findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(this);




        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == this.btnSubmit.getId()){
            String op = spinner.getSelectedItem().toString();
            spinnerPosition = spinner.getSelectedItemPosition();
            String x = etOperand.getText().toString();
            String input = op + x;
            if(!this.etOperand.getText().toString().equals("")){
                this.fragmentListener.changePage(1);
                this.fragmentListener.addList(input);
            }
            this.etOperand.setText("");
            this.operand = x;

        }

    }

    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fragmentListener =(FragmentListener)context;
        }else{
            throw new ClassCastException(context.toString()
            + "must implements Fragment Listener");
        }
    }

//        @Override
//    public void onPause(){
//        super.onPause();
//        this.saveList.saveOperator(etOperand.getText().toString());
//        this.saveList.saveOperand(this.operand);
//
//    }
//    //        Ketika aplikasi anda ditampilkan (resume), aplikasi akan meminta nilai yang tersimpan pada
//    //        PenyimpanNilaiDisplay dan menampilkannya ke EditTextBarang.
//    @Override
//    public void onResume(){
//        super.onResume();
//        this.etOperand.setText(this.saveList.getOperator());
//        this.s
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
