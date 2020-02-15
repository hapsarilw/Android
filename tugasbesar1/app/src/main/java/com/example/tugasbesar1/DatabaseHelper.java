package com.example.tugasbesar1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import java.util.ArrayList;
import java.util.HashMap;
import android.database.Cursor;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "CALCULATOR";

    // Table columns
    static abstract class MyColumns implements BaseColumns {
        public static final String _ID = "_id";
        public static final String OPERATOR = "operator";
        public static final String OPERAND = "operand";
        public static final String PREVIEW_RESULT = "preview_result";
    }


    // Database Information
    static final String DB_NAME = "CALCULATOR.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + MyColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MyColumns.OPERATOR + " STRING NOT NULL, "
            + MyColumns.OPERAND + " STRING NOT NULL, "
            + MyColumns.PREVIEW_RESULT + " STRING NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void insertOperationDetails(String operator, String operand, String preview_result){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(MyColumns.OPERATOR, operator);
        cValues.put(MyColumns.OPERAND, operand);
        cValues.put(MyColumns.PREVIEW_RESULT, preview_result);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME,null, cValues);
        db.close();
    }

    // Get User Details
    public ArrayList<HashMap<String, String>> GetCalculation(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> calcultaionList = new ArrayList<>();
        String query = "SELECT operator, operand, preview_result FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("operator",cursor.getString(cursor.getColumnIndex(MyColumns.OPERATOR)));
            user.put("operand",cursor.getString(cursor.getColumnIndex(MyColumns.OPERAND)));
            user.put("preview_result",cursor.getString(cursor.getColumnIndex(MyColumns.PREVIEW_RESULT)));
            calcultaionList.add(user);
        }
        return  calcultaionList;
    }

    // Get User Details based on userid
    public ArrayList<HashMap<String, String>> GetCalculationById(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_NAME;
        Cursor cursor = db.query(TABLE_NAME, new String[]{MyColumns.OPERATOR, MyColumns.OPERAND, MyColumns.PREVIEW_RESULT}, MyColumns._ID+ "=?",new String[]{String.valueOf(userid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("operator",cursor.getString(cursor.getColumnIndex(MyColumns.OPERATOR)));
            user.put("operand",cursor.getString(cursor.getColumnIndex(MyColumns.OPERAND)));
            user.put("preview_result",cursor.getString(cursor.getColumnIndex(MyColumns.PREVIEW_RESULT)));
            userList.add(user);
        }
        return  userList;
    }

    // Delete User Details
    public void DeleteCalculation(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, MyColumns._ID+" = ?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User Details
    public int UpdateCalculationDetails(String operator, String operand, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVals = new ContentValues();
        cVals.put(MyColumns.OPERATOR, operator);
        cVals.put(MyColumns.OPERAND, operand);
        int count = db.update(TABLE_NAME, cVals, MyColumns._ID+" = ?",new String[]{String.valueOf(id)});
        return  count;
    }
}
