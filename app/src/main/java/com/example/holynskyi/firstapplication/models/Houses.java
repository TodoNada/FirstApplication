package com.example.holynskyi.firstapplication.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.holynskyi.firstapplication.db.DatabaseSelectable;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;
import com.example.holynskyi.firstapplication.basic.BasicHouse;

import java.util.ArrayList;

/**
 * Created by holynskyi on 15.08.17.
 */

public class Houses<T extends BasicHouse> extends ArrayList<T> implements DatabaseSelectable {

    private SQLiteDatabase db;

    public Houses(SQLiteDatabase db)
    {
        this.db = db;
    }

    public Houses()
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

    public static Houses fromArrayList(ArrayList<House> basicArrayList)
    {
        Houses list = new Houses();
        for(int i=0; i<basicArrayList.size(); i++)
        {
            list.set(i, basicArrayList.get(i));
        }
        return list;
    }

    @Override
    public String getTableNameInDb() {
        return DatabaseStructure.tables.houses;
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
                    return true; // no rows in the db table, do something?

                while(!loader.isAfterLast())
                {
                    House house = new House();
                    house.setId(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.house.id)));
                    house.setCity(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.house.city)));
                    house.setAdress(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.house.adress)));
                    house.setOther(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.house.other)));
                    house.setData(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.house.someData)));
                    house.setUserId(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.house.userId)));

                    this.add((T)house);

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
}
