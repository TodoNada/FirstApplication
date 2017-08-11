package com.example.holynskyi.firstapplication.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.holynskyi.firstapplication.basic.BasicCar;
import com.example.holynskyi.firstapplication.db.DatabaseSelectable;
import com.example.holynskyi.firstapplication.db.DatabaseStructure;

import java.util.ArrayList;

/**
 * Created by holynskyi on 09.08.17.
 */

public class Cars<T extends BasicCar> extends ArrayList<T>  implements DatabaseSelectable{

    private SQLiteDatabase db;

    public Cars(SQLiteDatabase db)
    {
        this.db = db;
    }

    public Cars()
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

    public static Cars fromArrayList(ArrayList<Car> basicArrayList)
    {
        Cars list = new Cars();
        for(int i=0; i<basicArrayList.size(); i++)
        {
            list.set(i, basicArrayList.get(i));
        }
        return list;
    }


    @Override
    public String getTableNameInDb() {
        return DatabaseStructure.tables.cars;
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
                    return true; // no rows in the db table, do something?

                while(!loader.isAfterLast())
                {
                    Car car = new Car();
                    car.setId(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.car.id)));
                    car.setTitleOne(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.car.title1)));
                    car.setTitleTwo(loader.getString(loader.getColumnIndex(DatabaseStructure.columns.car.title2)));
                    car.setData(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.car.someData)));
                    car.setUserId(loader.getLong(loader.getColumnIndex(DatabaseStructure.columns.car.userId)));

                    this.add((T)car);

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
