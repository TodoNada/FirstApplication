package com.example.holynskyi.firstapplication.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.holynskyi.firstapplication.basic.BasicUser;
import com.example.holynskyi.firstapplication.db.DatabaseSelectable;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;

import java.util.ArrayList;

/**
 * Created by holynskyi on 09.08.17.
 */

public class Users<T extends BasicUser> extends ArrayList<T> implements DatabaseSelectable {

    private SQLiteDatabase db;

    public Users(SQLiteDatabase db)
    {
        this.db = db;
    }

    public Users()
    {
        this(null);
    }

    public SQLiteDatabase getDb()
    {
        return db;
    }

    public void setDb(SQLiteDatabase db)
    {
        this.db = db;
    }

    public static Users fromArrayList(ArrayList<User> basicArrayList)
    {
        Users list = new Users();
        for(int i=0; i<basicArrayList.size(); i++)
        {
            list.set(i, basicArrayList.get(i));
        }
        return list;
    }

    @Override
    public T get(int i)
    {
        return super.get(i);
    }



    @Override
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit) {
        try
        {
            if(db != null)
            {
                String sqlString = "SELECT * FROM "+getTableNameInDb();
                if(params.length > 0)
                    sqlString +=  " WHERE "+sqlCriteria;

                Cursor loader = db.rawQuery(sqlString,	params);

                if(!loader.moveToFirst())
                    return false; // no rows in the db table, do something?

                while(!loader.isAfterLast())
                {
                    User user = new User();
                    user.setId(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.user.id)));
                    user.setName(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.user.userName)));
                    user.setPassword(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.user.userPassword)));
                    user.setCars(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.user.userCars)));

                    this.add((T)user);

                    loader.moveToNext();
                }

                loader.close();
                return true;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public String getTableNameInDb() {
        return DatabaseStructure.tables.users;
    }
}
