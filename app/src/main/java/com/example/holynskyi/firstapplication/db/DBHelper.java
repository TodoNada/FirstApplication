package com.example.holynskyi.firstapplication.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by holynskyi on 09.08.17.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final int DB_VERSION = 42;
    static final String DB_NAME = "user_cars.db";

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL("CREATE TABLE "+ DatabaseStructure.tables.users+" (" +
                DatabaseStructure.columns.user.id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseStructure.columns.user.userName+" TEXT, " +
                DatabaseStructure.columns.user.userPassword+" TEXT, " +
                DatabaseStructure.columns.user.userCars+" INTEGER "+
                ")");

        // create reminders table
        db.execSQL("CREATE TABLE "+ DatabaseStructure.tables.cars+" (" +
                DatabaseStructure.columns.car.id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseStructure.columns.car.title1+" TEXT, "+
                DatabaseStructure.columns.car.title2+" TEXT, "+
                DatabaseStructure.columns.car.someData+" INTEGER, " +
                DatabaseStructure.columns.car.userId+" INTEGER" +
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
