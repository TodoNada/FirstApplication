package com.example.holynskyi.firstapplication.db;

/**
 * Created by holynskyi on 09.08.17.
 */

public interface DatabaseStorable {
    public String getTableNameInDb();
    public boolean insert();
    public boolean update();
    public boolean remove();
}
