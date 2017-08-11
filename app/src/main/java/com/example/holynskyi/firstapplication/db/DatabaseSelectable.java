package com.example.holynskyi.firstapplication.db;

/**
 * Created by holynskyi on 09.08.17.
 */

public interface DatabaseSelectable {
    public String getTableNameInDb();
    public boolean loadFromDb(String sqlCriteria, String[] params, int limit);
}
