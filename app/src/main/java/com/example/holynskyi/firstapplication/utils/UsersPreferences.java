package com.example.holynskyi.firstapplication.utils;

/**
 * Created by holynskyi on 11.08.17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class UsersPreferences {

    private String fileName;
    private Context context;
    private int mode;
    private String usersNamesField = "names";
    private SharedPreferences sharedPreferences;
    private Set<String> set;
    private static UsersPreferences usersPreferences;


    public static final UsersPreferences getInstance(final Context context, final String fileName, int mode) {
        if (usersPreferences == null) {
            usersPreferences = new UsersPreferences(context,fileName,mode);
        }
        return usersPreferences;
    }

    private UsersPreferences(final Context context, final String fileName, int mode) {
        this.context = context;
        this.fileName = fileName;
        this.mode = mode;
        sharedPreferences =  this.context.getSharedPreferences(this.fileName,this.mode);
        updateInnerSet();
    }

    private void updateInnerSet() {
        set = new HashSet<>(sharedPreferences.getStringSet(usersNamesField, new HashSet<String>()));
    }

    protected void setUsersNameFields(String usersNamesField) {
        this.usersNamesField = usersNamesField;
    }

    protected String getUsersNameFields(String usersNamesField) {
        return usersNamesField;
    }


    /**
     *
     * @return Set<String>, that contains user names in preferences field, named "usersNamesField"
     * @return If there are no such a data, then return null
     */
    public final Set<String> getStringSet() {
        return set;
    }

    public boolean putUserNameIntoPreferences(String userName) {
       if (userName==null) return false;
        Log.d("putting string ",userName);
        set.add(userName);
        if (!sharedPreferences.edit().putStringSet(usersNamesField,set).commit()) return false;
        updateInnerSet();
        return true;
    }

}
