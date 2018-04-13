/*
 * Copyright (c) 2018. Faisal Jamil
 */

package com.example.faisal.data.service.adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.VisibleForTesting;

import com.example.faisal.domain.models.BirthDayRecord;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


public class LocalDbHandler extends SQLiteOpenHelper {
    private static final String TAG = "LocalDBHandler";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "localDB";
    // Birthday record table name
    private static final String TABLE_BIRTHDAY_RECORD = "birthdayrecord";
    // Birthday record Table Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";

    @Inject
    public LocalDbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_BIRTHDAY_RECORD_TABLE = "CREATE TABLE " + TABLE_BIRTHDAY_RECORD + " ("
                + KEY_NAME + " TEXT,"
                + KEY_DATE + " TEXT"
                +" )";

        sqLiteDatabase.execSQL(CREATE_BIRTHDAY_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRTHDAY_RECORD);
        // Creating tables again
        onCreate(sqLiteDatabase);
    }

    /*  BIRTHDAY TABLE FUNCTIONS */

    // Getting All Records
    public Single<List<BirthDayRecord>> getAllRecords() {
        List<BirthDayRecord> recordList = new ArrayList<BirthDayRecord>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_BIRTHDAY_RECORD;

        @VisibleForTesting
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null){
            Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BirthDayRecord birthDayRecord = new BirthDayRecord();
                    birthDayRecord.name = cursor.getString(0);
                    birthDayRecord.date = cursor.getString(1);
                    // Adding record to list
                    recordList.add(birthDayRecord);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        db.close();
        // return birthday record list
        return Single.just(recordList);
    }

    // Adding new birthday record
    public Single<Long> addRecord(BirthDayRecord birthDayRecord) {

        @VisibleForTesting
        SQLiteDatabase db = this.getWritableDatabase();
        if(db != null){ // to avoid nullpointer exception
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, birthDayRecord.name);
            values.put(KEY_DATE, birthDayRecord.date);

            // Inserting Row
            Single<Long> result = Single.just(db.insert(TABLE_BIRTHDAY_RECORD, null, values));
            db.close(); // Closing database connection
            return result;
        }
        return null;
    }

    // Get record count
    public Single<Integer> getRecordCount() {
        String countQuery = "SELECT * FROM " + TABLE_BIRTHDAY_RECORD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        Single<Integer> result = Single.just(cursor.getCount());
        db.close();
        return result;
    }
}