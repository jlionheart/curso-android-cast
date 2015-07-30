package com.curso_android_cast.cursoandroidcast.model.persistance.contract;


import android.content.ContentValues;
import android.database.Cursor;

import com.curso_android_cast.cursoandroidcast.model.entity.User;

public class UserContract {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String USER_NAME = "user_name";
    public static final String PASSWORD = "password";

    public static final String[] COLUMNS = { ID, NAME, USER_NAME, PASSWORD };

    public static final String getCreateTable(){
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(USER_NAME + " TEXT, ");
        sql.append(PASSWORD + " TEXT ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static User bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()){
            User user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(UserContract.NAME)));
            user.setUserName(cursor.getString(cursor.getColumnIndex(UserContract.USER_NAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));

            return user;
        }

        return null;
    }

    public static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues();
        values.put(UserContract.ID, user.getId());
        values.put(UserContract.NAME, user.getName());
        values.put(UserContract.USER_NAME, user.getUserName());
        values.put(UserContract.PASSWORD, user.getPassword());
        return values;
    }
}
