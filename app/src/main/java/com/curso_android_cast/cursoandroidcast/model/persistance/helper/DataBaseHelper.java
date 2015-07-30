package com.curso_android_cast.cursoandroidcast.model.persistance.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.curso_android_cast.cursoandroidcast.model.persistance.contract.ClientContract;
import com.curso_android_cast.cursoandroidcast.model.persistance.contract.UserContract;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATA_BASE_NAME = "MY_DATABASE";
    public static final int VERSION = 1;

    public DataBaseHelper(Context context){
        super(context, DataBaseHelper.DATA_BASE_NAME, null, DataBaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClientContract.getSqlCreateTable());
        db.execSQL(UserContract.getCreateTable());
        db.execSQL(DataBaseDefaultRecordHelper.createAdminUser());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
