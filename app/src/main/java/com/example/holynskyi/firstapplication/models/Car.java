package com.example.holynskyi.firstapplication.models;

import com.example.holynskyi.firstapplication.basic.BasicCar;
import com.example.holynskyi.firstapplication.db.DatabaseStorable;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by holynskyi on 09.08.17.
 */

public class Car extends BasicCar implements DatabaseStorable {
    private SQLiteDatabase db;

    public Car(SQLiteDatabase db)
    {
        this.db = db;
    }

    public Car() {
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
        return DatabaseStructure.tables.cars;
    }

    @Override
    public boolean insert() {
        if(db != null)
        {
            try {
                String insertSql = "INSERT INTO " + getTableNameInDb() + " (" +
                        DatabaseStructure.columns.car.title1 + ", " +
                        DatabaseStructure.columns.car.title2 + ", " +
                        DatabaseStructure.columns.car.someData + ", " +
                        DatabaseStructure.columns.car.userId + " " +
                        ") VALUES (?,?,?,?)";

                int n = 1;
                SQLiteStatement ss = db.compileStatement(insertSql);
                ss.bindString(n++, getTitleOne());
                ss.bindString(n++, getTitleTwo());
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
                        DatabaseStructure.columns.car.id + " = ?, " +
                        DatabaseStructure.columns.car. title1+ " = ?, " +
                        DatabaseStructure.columns.car.title2 + " = ?, " +
                        DatabaseStructure.columns.car.someData + " = ?, " +
                        DatabaseStructure.columns.car.userId + " = ?" +
                        " WHERE " + DatabaseStructure.columns.car.id + " = ?";

                int n = 1;
                SQLiteStatement ss = db.compileStatement(updateSql);

                ss.bindLong(n++, getId());
                ss.bindString(n++, getTitleOne());
                ss.bindString(n++, getTitleTwo());
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
                String removeSql = "DELETE FROM " + getTableNameInDb() + " WHERE " + DatabaseStructure.columns.car.id + "= ?";
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
