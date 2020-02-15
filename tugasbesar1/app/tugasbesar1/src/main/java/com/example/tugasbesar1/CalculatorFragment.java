package com.example.tugasbesar1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton fab;
    Button btnResult;
    Button btnClear;
    Button btnSave;

    ListView listView;
    List<Item> itemList;
    Adapter adapter;
    SaveList saveList;


    FragmentListener fragmentListener;



    public CalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        listView = (ListView) view.findViewById(R.id.listview_number);
        itemList = new ArrayList<>();
        adapter = new Adapter(getActivity(), itemList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        this.fab = (FloatingActionButton) view.findViewById(R.id.fab_add);
        this.fab.setOnClickListener(this);


        this.btnResult = view.findViewById(R.id.btn_result);
        this.btnResult.setOnClickListener(this);

        this.btnClear = view.findViewById(R.id.btn_clear);
        this.btnClear.setOnClickListener(this);

        this.btnSave = view.findViewById(R.id.btn_save);
        this.btnSave.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == this.fab.getId()){
            this.fragmentListener.changePage(2);
        }
        if(view.getId() == this.btnResult.getId()){
            if(this.itemList.size()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("RESULT");
                builder.setMessage("The result is: " + "0");
                builder.setCancelable(true);
                builder.setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder.create();
                alert11.show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("RESULT");
                builder.setMessage("The result is: " + count());
                builder.setCancelable(true);
                builder.setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder.create();
                alert11.show();
            }
        }
        if(view.getId() == this.btnClear.getId()){
            this.fragmentListener.clearList();
        }


    }

    public String count() {
        String temp = "0";
        for (int i = 0; i < itemList.size(); i++) {
            temp = temp + itemList.get(i).getNumber();
        }

        List<Character> listOperator = new ArrayList<>();
        List<Integer> listOperand = new ArrayList<>();
        String tempOperand = "";

        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '+' || temp.charAt(i) == '-' || temp.charAt(i) == '*' || temp.charAt(i) == '/') {
                listOperator.add(temp.charAt(i));
                if (tempOperand != null) {
                    listOperand.add(Integer.parseInt(tempOperand));
                    tempOperand = "";
                }
            } else {
                tempOperand += temp.charAt(i);
            }

        }
        int x = Integer.parseInt(tempOperand);
        listOperand.add(x);

        int indexOperator = 0;
         double result = 0;

        for (int i = 0; i < listOperand.size(); i++) {
            if (listOperator.get(indexOperator).equals('+')) {
                if (indexOperator == 0) {
                    int input = listOperand.get(i) + listOperand.get(i + 1);
                    result = result + input;
                    i++;
                    indexOperator++;
                } else {
                    result = result + listOperand.get(i);
                    indexOperator++;
                }

            } else if (listOperator.get(indexOperator).equals('-')) {
                if (indexOperator == 0) {
                    int input = listOperand.get(i) + listOperand.get(i + 1);
                    result = result - input;
                    i++;
                    indexOperator++;
                } else {
                    result = result - listOperand.get(i);
                    indexOperator++;
                }

            } else if (listOperator.get(indexOperator).equals('*')) {
                if (indexOperator == 0) {
                    int input = listOperand.get(i) * listOperand.get(i + 1);
                    result = result * input;
                    i++;
                    indexOperator++;
                } else {
                    result = result * listOperand.get(i);
                    indexOperator++;
                }
            }
            else if (listOperator.get(indexOperator).equals('/')) {
                if (indexOperator == 0) {
                    int input = listOperand.get(i) / listOperand.get(i + 1);
                    result = 0;
                    i++;
                    indexOperator++;
                } else {
                    result = result / listOperand.get(i);
                    indexOperator++;
                }
            }
        }
        return result + "";
    }



//    public boolean save(){
//        SharedPreferences sp = getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        Set<Item> set = new HashSet<>();
//        set.addAll(itemList);
//        editor.putString("list", String.valueOf(set));
//        return editor.commit();
//    }



    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fragmentListener = (FragmentListener) context;
        }else{
            throw new ClassCastException(context.toString()
            + "must implement Fragment Listener");
        }
    }






}
