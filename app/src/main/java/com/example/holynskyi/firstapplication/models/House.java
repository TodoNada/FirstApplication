package com.example.holynskyi.firstapplication.models;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.holynskyi.firstapplication.basic.BasicHouse;
import com.example.holynskyi.firstapplication.db.DatabaseStorable;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;

/**
 * Created by holynskyi on 15.08.17.
 */

public class House extends BasicHouse implements DatabaseStorable {
    private SQLiteDatabase db;


    public House(SQLiteDatabase db)
    {
        this.db = db;
    }

    public House() {
        this(null);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }


    @Override
    public String getTableNameInDb() {
        return DatabaseStructure.tables.houses;
    }


    @Override
    public boolean insert() {
        if(db != null)
        {
            try {
                String insertSql = "INSERT INTO " + getTableNameInDb() + " (" +
                        DatabaseStructure.columns.house.city + ", " +
                        DatabaseStructure.columns.house.adress + ", " +
                        DatabaseStructure.columns.house.other + ", " +
                        DatabaseStructure.columns.house.someData + ", " +
                        DatabaseStructure.columns.house.userId + " " +
                        ") VALUES (?,?,?,?,?)";

                int n = 1;
                SQLiteStatement ss = db.compileStatement(insertSql);
                ss.bindString(n++, getCity());
                ss.bindString(n++, getAdress());
                ss.bindString(n++, getOther());
                ss.bindLong(n++, getData());
                ss.bindLong(n++, getUserId());

                ss.execute();
                ss.close();

                setId(getId());

                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update() {
        if(db != null)
        {
            try
            {

                String updateSql = "UPDATE " + getTableNameInDb() + " SET " +
                        DatabaseStructure.columns.house.id + " = ?, " +
                        DatabaseStructure.columns.house.city + " = ?, " +
                        DatabaseStructure.columns.house.adress + " = ?, " +
                        DatabaseStructure.columns.house.other + " = ?, " +
                        DatabaseStructure.columns.house.someData + " = ?, " +
                        DatabaseStructure.columns.house.userId + " = ?" +
                        " WHERE " + DatabaseStructure.columns.house.id + " = ?";

                int n = 1;
                SQLiteStatement ss = db.compileStatement(updateSql);

                ss.bindLong(n++, getId());
                ss.bindString(n++, getCity());
                ss.bindString(n++, getAdress());
                ss.bindString(n++, getOther());
                ss.bindLong(n++, getData());
                ss.bindLong(n++, getUserId());

                ss.bindLong(n++, getId());

                ss.execute();
                ss.close();

                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return false;


    }

    @Override
    public boolean remove() {
        if(db != null && getId() > 0)
        {
            try {
                String removeSql = "DELETE FROM " + getTableNameInDb() + " WHERE " + DatabaseStructure.columns.house.id + "= ?";
                int n = 1;
                SQLiteStatement ss = db.compileStatement(removeSql);
                ss.bindLong(n++, getId());
                ss.execute();
                ss.close();
                return true;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;


    }
}
