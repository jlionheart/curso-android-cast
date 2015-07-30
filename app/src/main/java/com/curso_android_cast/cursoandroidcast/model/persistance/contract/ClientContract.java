package com.curso_android_cast.cursoandroidcast.model.persistance.contract;

import android.content.ContentValues;
import android.database.Cursor;

import com.curso_android_cast.cursoandroidcast.model.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientContract {

    public static final String TABLE = "client";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_TYPE = "address_type";
    public static final String DISTRICT = "district";
    public static final String CITY = "city";
    public static final String ZIP_CODE = "zip_code";
    public static final String PROVINCE = "province";

    public static final String[] COLUMNS = { ID, NAME, AGE, PHONE, ADDRESS, ADDRESS_TYPE, DISTRICT, CITY, ZIP_CODE, PROVINCE };

    public static String getSqlCreateTable(){
        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(NAME + " TEXT, ");
        sql.append(AGE + " INTEGER, ");
        sql.append(PHONE + " TEXT, ");
        sql.append(ADDRESS + " TEXT, ");
        sql.append(ADDRESS_TYPE + " TEXT NULL, ");
        sql.append(DISTRICT + " TEXT NULL, ");
        sql.append(CITY + " TEXT NULL, ");
        sql.append(ZIP_CODE + " TEXT NULL, ");
        sql.append(PROVINCE + " TEXT NULL ");
        sql.append(" ) ");

        return sql.toString();
    }

    public static Client bind(Cursor cursor){
        if(!cursor.isBeforeFirst() || cursor.moveToNext()){
            Client client = new Client();
            client.setId(cursor.getInt(cursor.getColumnIndex(ClientContract.ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(ClientContract.NAME)));
            client.setAge(cursor.getInt(cursor.getColumnIndex(ClientContract.AGE)));
            client.setPhone(cursor.getString(cursor.getColumnIndex(ClientContract.PHONE)));
            client.getAddress().setAddress(cursor.getString(cursor.getColumnIndex(ClientContract.ADDRESS)));
            client.getAddress().setAddressType(cursor.getString(cursor.getColumnIndex(ClientContract.ADDRESS_TYPE)));
            client.getAddress().setDistrict(cursor.getString(cursor.getColumnIndex(ClientContract.DISTRICT)));
            client.getAddress().setCity(cursor.getString(cursor.getColumnIndex(ClientContract.CITY)));
            client.getAddress().setZipCode(cursor.getString(cursor.getColumnIndex(ClientContract.ZIP_CODE)));
            client.getAddress().setProvince(cursor.getString(cursor.getColumnIndex(ClientContract.PROVINCE)));

            return client;
        }

        return null;
    }

    public static List<Client> bindList(Cursor cursor){
        List<Client> clients = new ArrayList<Client>();

        while (cursor.moveToNext()){
            clients.add(bind(cursor));
        }

        return clients;
    }

    public static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(ClientContract.ID, client.getId());
        values.put(ClientContract.NAME, client.getName());
        values.put(ClientContract.AGE, client.getAge());
        values.put(ClientContract.PHONE, client.getPhone());
        values.put(ClientContract.ADDRESS, client.getAddress().getAddress());
        values.put(ClientContract.ADDRESS_TYPE, client.getAddress().getAddressType());
        values.put(ClientContract.DISTRICT, client.getAddress().getDistrict());
        values.put(ClientContract.CITY, client.getAddress().getCity());
        values.put(ClientContract.ZIP_CODE, client.getAddress().getZipCode());
        values.put(ClientContract.PROVINCE, client.getAddress().getProvince());
        return values;
    }
}
