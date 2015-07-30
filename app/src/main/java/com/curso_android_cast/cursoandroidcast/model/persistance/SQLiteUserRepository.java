package com.curso_android_cast.cursoandroidcast.model.persistance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.curso_android_cast.cursoandroidcast.model.entity.User;
import com.curso_android_cast.cursoandroidcast.model.persistance.contract.UserContract;
import com.curso_android_cast.cursoandroidcast.model.persistance.helper.DataBaseHelper;
import com.curso_android_cast.cursoandroidcast.model.persistance.interfaces.UserRepository;
import com.curso_android_cast.cursoandroidcast.util.AppUtil;


public class SQLiteUserRepository implements UserRepository {

    private static SQLiteUserRepository singletonInstance;

    private SQLiteUserRepository(){
        super();
    }

    public static UserRepository getInstance(){
        if(singletonInstance == null)
            singletonInstance = new SQLiteUserRepository();

        return singletonInstance;
    }

    @Override
    public void save(User user){
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = UserContract.getContentValues(user);

        db.insert(UserContract.TABLE, null, values);

        db.close();
        helper.close();
    }

    @Override
    public boolean login(User user){
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        String where = UserContract.USER_NAME + " = ? AND " + UserContract.PASSWORD + " = ?";
        String[] args = { user.getUserName(), user.getPassword() };
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, where, args, null, null, null);

        User dataBaseUser = UserContract.bind(cursor);

        db.close();
        helper.close();

        return dataBaseUser != null;
    }
}
