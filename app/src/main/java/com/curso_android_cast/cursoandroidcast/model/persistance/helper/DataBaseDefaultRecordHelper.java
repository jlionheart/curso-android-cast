package com.curso_android_cast.cursoandroidcast.model.persistance.helper;

import com.curso_android_cast.cursoandroidcast.model.persistance.contract.UserContract;

public final class DataBaseDefaultRecordHelper {

    private DataBaseDefaultRecordHelper(){
        super();
    }

    public static String createAdminUser(){
        StringBuilder sql = new StringBuilder();
        String name = "Admin";
        String userName = "admin";
        String password = "admin";

        sql.append(" INSERT INTO ");
        sql.append(UserContract.TABLE);
        sql.append(" ( ");
        sql.append(UserContract.NAME + " , ");
        sql.append(UserContract.USER_NAME + " , ");
        sql.append(UserContract.PASSWORD);
        sql.append(" ) ");
        sql.append(" VALUES ");
        sql.append(" ( ");
        sql.append("'" + name + "' , ");
        sql.append("'" + userName + "' , ");
        sql.append("'" + password + "'");
        sql.append(" ) ");

        return sql.toString();
    }
}
