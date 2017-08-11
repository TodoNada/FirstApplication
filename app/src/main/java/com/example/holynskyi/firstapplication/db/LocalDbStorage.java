package com.example.holynskyi.firstapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by holynskyi on 09.08.17.
 */

public class LocalDbStorage {
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public LocalDbStorage(Context context)
    {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void reopen()
    {
        db = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        try
        {
            db.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase getDb()
    {
        return db;
    }

}
