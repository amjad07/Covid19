package com.worklation.covid19.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.worklation.covid19.model.Collector;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "symptomsManager";
    private static final String TABLE_SYMPTOMS = "symptoms";
    private static final String KEY_ID = "id";
    private static final String KEY_HEART_RATE = "heart_rate";
    private static final String KEY_RESP_RATE = "resp_rate";

    private static final String KEY_NAUSEA = "nausea";
    private static final String KEY_HEADACHE = "headache";
    private static final String KEY_DIARRHEA = "diarrhea";
    private static final String KEY_S_T = "soar_throat";
    private static final String KEY_F = "fever";
    private static final String KEY_M_A = "muscle_ache";
    private static final String KEY_L_S_T = "loss_smell";
    private static final String KEY_C = "cough";
    private static final String KEY_S_B = "shortness";
    private static final String KEY_F_T = "feeling_tired";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_symptomsS_TABLE = "CREATE TABLE " + TABLE_SYMPTOMS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_HEART_RATE + " TEXT,"
                + KEY_RESP_RATE + " TEXT" + ","
                + KEY_NAUSEA + " TEXT" + ","
                + KEY_HEADACHE + " TEXT" + ","
                + KEY_DIARRHEA + " TEXT" + ","
                + KEY_S_T + " TEXT" + ","
                + KEY_F + " TEXT" + ","
                + KEY_M_A + " TEXT" + ","
                + KEY_L_S_T + " TEXT" + ","
                + KEY_C + " TEXT" + ","
                + KEY_S_B + " TEXT" + ","
                + KEY_F_T + " TEXT" +
                ")";
        db.execSQL(CREATE_symptomsS_TABLE);

        //default entry of database

        ContentValues values = new ContentValues();
        values.put(KEY_HEART_RATE, ""); // symptoms Name
        values.put(KEY_RESP_RATE, "");

        // Inserting Row
        db.insert(TABLE_SYMPTOMS, null, values);
    }
    // code to get all symptomss in a list view
    public Collector getAllSymptomss() {
        List<Collector> collectorList = new ArrayList<Collector>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SYMPTOMS;
        Collector collector = new Collector();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {


            collector.setID(Integer.parseInt(cursor.getString(0)));
            collector.setHeartRate(cursor.getString(1));
            collector.setRespRate(cursor.getString(2));
            collector.setNausea(cursor.getString(3));
            collector.setHeadache(cursor.getString(4));
            collector.setDiarrhea(cursor.getString(5));
            collector.setSoar_throat(cursor.getString(6));
            collector.setFever(cursor.getString(7));
            collector.setMuscle_ache(cursor.getString(8));
            collector.setLoss_of_smell(cursor.getString(9));
            collector.setCough(cursor.getString(10));
            collector.setShortness_of_breath(cursor.getString(11));
            collector.setFeeling_tired(cursor.getString(12));
            // Adding symptoms to list
            collectorList.add(collector);

        }

        // return symptoms list
        return collector;
    }
    // code to get the single symptoms
    Collector getSymptoms(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SYMPTOMS, new String[] { KEY_ID,
                        KEY_HEART_RATE, KEY_RESP_RATE,KEY_NAUSEA,KEY_HEADACHE,KEY_DIARRHEA,
                        KEY_S_T,KEY_F,KEY_M_A,KEY_L_S_T,KEY_C,KEY_S_B,KEY_F_T

                }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Collector collector = new Collector(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return symptoms
        return collector;
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYMPTOMS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new symptoms
    public void addSigns(Collector collector) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HEART_RATE, collector.getHeartRate()); // symptoms Name
        values.put(KEY_RESP_RATE, collector.getRespRate());

        // Inserting Row
        db.insert(TABLE_SYMPTOMS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to add the new symptoms
    public  void addSymptoms(Collector collector) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAUSEA, collector.getNausea());
        values.put(KEY_HEADACHE, collector.getHeadache());
        values.put(KEY_DIARRHEA, collector.getDiarrhea());
        values.put(KEY_S_T, collector.getSoarThroat());
        values.put(KEY_F, collector.getFever());
        values.put(KEY_M_A, collector.getMuscleAche());
        values.put(KEY_L_S_T, collector.getLossOfSmell());
        values.put(KEY_C, collector.getCough());
        values.put(KEY_S_B, collector.getShortnessOfBreath());
        values.put(KEY_F_T, collector.getFeelingTired());

        // Inserting Row
        db.insert(TABLE_SYMPTOMS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }





    // code to update the single symptoms
    public int updateRespRate(Collector collector) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RESP_RATE, collector.getRespRate());

        // updating row
        return db.update(TABLE_SYMPTOMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1) });
    }

    // code to update the single symptoms
    public int updateHeartRate(Collector collector) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HEART_RATE, collector.getHeartRate());

        // updating row
        return db.update(TABLE_SYMPTOMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1) });
    }

    // code to update the single symptoms
    public int updateSymptoms(Collector collector) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAUSEA, collector.getNausea());
        values.put(KEY_HEADACHE, collector.getHeadache());
        values.put(KEY_DIARRHEA, collector.getDiarrhea());
        values.put(KEY_S_T, collector.getSoarThroat());
        values.put(KEY_F, collector.getFever());
        values.put(KEY_M_A, collector.getMuscleAche());
        values.put(KEY_L_S_T, collector.getLossOfSmell());
        values.put(KEY_C, collector.getCough());
        values.put(KEY_S_B, collector.getShortnessOfBreath());
        values.put(KEY_F_T, collector.getFeelingTired());

        // updating row
        return db.update(TABLE_SYMPTOMS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(1) });
    }

    // Deleting single symptoms
    public void deleteSymptoms(Collector collector) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SYMPTOMS, KEY_ID + " = ?",
                new String[] { String.valueOf(collector.getID()) });
        db.close();
    }

    // Getting symptomss Count
    public int getSymptomsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SYMPTOMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}