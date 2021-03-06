package com.example.weightcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Iterator;

public class LiftDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "LiftCalculator.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Categories";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORY_NAME = "category_name";
    private static final String COLUMN_LIFT_NAME = "lift_name";
    private static final String COLUMN_WEIGHT = "weight";

    private static ArrayList<String> tableNames;

    public static Boolean convertToPounds;
    public static Boolean roundKilos;

    public LiftDatabase(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db){
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_CATEGORY_NAME + " TEXT);";
        db.execSQL(query);
        tableNames = new ArrayList<>();
        convertToPounds = false;
        roundKilos = false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        for (String table:tableNames) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(db);
    }

    void addCategory(String categoryName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Remove spaces from category name
        categoryName = categoryName.replaceAll("\\s+","");

        //check if table already exists
        if(tableNames.contains(categoryName)){
            Toast.makeText(context, "Table already exists, please choose a different name", Toast.LENGTH_SHORT).show();
        } else {
            //Create new table for category
            String query =
                    "CREATE  TABLE " + categoryName +
                            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_LIFT_NAME + " TEXT, " +
                            COLUMN_WEIGHT + " REAL);";
            db.execSQL(query);

            tableNames.add(categoryName);

            cv.put(COLUMN_CATEGORY_NAME, categoryName);
            long result = db.insert(TABLE_NAME, null, cv);
            if(result == -1){
                Toast.makeText(context, "Failed to insert data.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void addLift(String categoryName, String liftName, float maxWeight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_LIFT_NAME, liftName);
        cv.put(COLUMN_WEIGHT, maxWeight);

        long result = db.insert(categoryName, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to insert data.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(String tableName){
        String query = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String tableName, String liftName, String liftWeight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LIFT_NAME, liftName);
        cv.put(COLUMN_WEIGHT, liftWeight);

        long result = db.update(tableName, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRecord(String row_id, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(tableName, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteCategory(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        tableNames.remove(tableName);
        long result = db.delete(TABLE_NAME, "category_name=?", new String[]{tableName});
        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Category deleted", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        //Drop all subcategory tables
        while(tableNames.size() > 0){
            db.execSQL("DROP TABLE IF EXISTS " + tableNames.get(0));
            tableNames.remove(0);
        }
        //Remove all records from the category table
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
