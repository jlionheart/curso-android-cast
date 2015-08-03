package com.curso_android_cast.cursoandroidcast.model.persistance.helper;

import com.curso_android_cast.cursoandroidcast.model.persistance.contract.UserContract;
import com.curso_android_cast.cursoandroidcast.util.PasswordUtil;

public final class DataBaseDefaultRecordHelper {

    private DataBaseDefaultRecordHelper(){
        super();
    }

    public static String createAdminUser(){
        StringBuilder sql = new StringBuilder();
        String name = "Admin";
        String userName = "admin";
        String salt = PasswordUtil.generateRandomSalt();
        String password = PasswordUtil.generatePasswordHash("admin", salt);

        sql.append(" INSERT INTO ");
        sql.append(UserContract.TABLE);
        sql.append(" ( ");
        sql.append(UserContract.NAME + " , ");
        sql.append(UserContract.USER_NAME + " , ");
        sql.append(UserContract.PASSWORD + " , ");
        sql.append(UserContract.SALT);
        sql.append(" ) ");
        sql.append(" VALUES ");
        sql.append(" ( ");
        sql.append("'" + name + "' , ");
        sql.append("'" + userName + "' , ");
        sql.append("'" + password + "' , ");
        sql.append("'" + salt + "'");
        sql.append(" ) ");

        return sql.toString();
    }
}
