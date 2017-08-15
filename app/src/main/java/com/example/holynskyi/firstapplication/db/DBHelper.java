package com.example.holynskyi.firstapplication.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by holynskyi on 09.08.17.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final int DB_VERSION = 43;
    static final String DB_NAME = "user_cars_houses.db";

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create user table
        db.execSQL("CREATE TABLE "+ DatabaseStructure.tables.users+" (" +
                DatabaseStructure.columns.user.id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseStructure.columns.user.userName+" TEXT, " +
                DatabaseStructure.columns.user.userPassword+" TEXT, " +
                DatabaseStructure.columns.user.userCars+" INTEGER "+
                ")");

        // create cars table
        db.execSQL("CREATE TABLE "+ DatabaseStructure.tables.cars+" (" +
                DatabaseStructure.columns.car.id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseStructure.columns.car.title1+" TEXT, "+
                DatabaseStructure.columns.car.title2+" TEXT, "+
                DatabaseStructure.columns.car.someData+" INTEGER, " +
                DatabaseStructure.columns.car.userId+" INTEGER" +
                ")");

        // create houses table
        db.execSQL("CREATE TABLE "+ DatabaseStructure.tables.houses+" (" +
                DatabaseStructure.columns.house.id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseStructure.columns.house.city+" TEXT, "+
                DatabaseStructure.columns.house.adress+" TEXT, "+
                DatabaseStructure.columns.house.other+" TEXT, "+
                DatabaseStructure.columns.house.someData+" INTEGER, " +
                DatabaseStructure.columns.house.userId+" INTEGER" +
                ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
