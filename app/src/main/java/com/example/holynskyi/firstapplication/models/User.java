package com.example.holynskyi.firstapplication.models;

import com.example.holynskyi.firstapplication.basic.BasicUser;
import com.example.holynskyi.firstapplication.db.DatabaseStorable;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by holynskyi on 09.08.17.
 */

public class User extends BasicUser implements DatabaseStorable {

    //database for storage
    private SQLiteDatabase db;

    public User(SQLiteDatabase db) {
        this.db = db;
        this.id = -1;
    }

    public User() {
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
        return DatabaseStructure.tables.users;
    }

    @Override
    public boolean insert() {
        if(db != null) {
            try {
                String insertSql = "INSERT INTO " + getTableNameInDb() + " (" +
                        DatabaseStructure.columns.user.userName + ", " +
                        DatabaseStructure.columns.user.userPassword + ", " +
                        DatabaseStructure.columns.user.userCars + " " +
                        ") VALUES (?,?,?)";


                int n = 1;
                SQLiteStatement ss = db.compileStatement(insertSql);
                ss.bindString(n++,getName());
                ss.bindString(n++,getPassword());
                ss.bindLong(n++, getCars());

                long localId = ss.executeInsert();

                setId(localId);

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
    public boolean update() {
        if(db != null)
        {
            try
            {

                String updateSql = "UPDATE " + getTableNameInDb() + " SET " +
                        DatabaseStructure.columns.user.id + " = ?, " +
                        DatabaseStructure.columns.user.userName + " = ?, " +
                        DatabaseStructure.columns.user.userPassword + " = ?, " +
                        DatabaseStructure.columns.user.userCars + " = ?" +
                        " WHERE " + DatabaseStructure.columns.user.id + " = ?";

                int n = 1;
                SQLiteStatement ss = db.compileStatement(updateSql);

                ss.bindLong(n++, getId());
                ss.bindString(n++,getName());
                ss.bindString(n++,getPassword());
                ss.bindLong(n++, getCars());

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
                String removeSql = "DELETE FROM " + getTableNameInDb() + " WHERE " + DatabaseStructure.columns.user.id + "= ?";
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
