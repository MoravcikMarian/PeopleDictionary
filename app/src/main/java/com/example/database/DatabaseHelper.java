package com.example.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PeopleDirectory.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "directory";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "persons_name";
    private static final String COLUMN_GENDER = "persons_gender";
    private static final String COLUMN_AGE = "persons_age";
    private static final String COLUMN_ADDRESS = "persons_address";
    private static final String COLUMN_CITY = "persons_city";
    private static final String COLUMN_ZIP = "persons_zip";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_CITY + " TEXT, " +
                COLUMN_ZIP + " INTEGER);";

        database.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public void addPerson(PersonalDetails details) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, details.name);
        cv.put(COLUMN_GENDER, details.gender);
        cv.put(COLUMN_AGE, details.age);
        cv.put(COLUMN_ADDRESS, details.address);
        cv.put(COLUMN_CITY, details.city);
        cv.put(COLUMN_ZIP, details.zip);

        database.insert(TABLE_NAME,null, cv);
    }

    public Cursor findInDatabase(String[] columns, String selection, String[] selectionArgs) {
        SQLiteDatabase database = this.getReadableDatabase();

        return database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, null);
    }

    @SuppressLint("Recycle")
    public boolean isEmpty() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        return database.rawQuery(query, null).getCount() == 0;
    }
}
