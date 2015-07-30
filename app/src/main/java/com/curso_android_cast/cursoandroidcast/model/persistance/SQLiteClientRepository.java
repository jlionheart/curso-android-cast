package com.curso_android_cast.cursoandroidcast.model.persistance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.curso_android_cast.cursoandroidcast.model.entity.Client;
import com.curso_android_cast.cursoandroidcast.model.persistance.contract.ClientContract;
import com.curso_android_cast.cursoandroidcast.model.persistance.helper.DataBaseHelper;
import com.curso_android_cast.cursoandroidcast.model.persistance.interfaces.ClientRepository;
import com.curso_android_cast.cursoandroidcast.util.AppUtil;

import java.util.List;

public class SQLiteClientRepository implements ClientRepository {

    private static SQLiteClientRepository singletonInstance;

    private SQLiteClientRepository(){
        super();
    }

    public static ClientRepository getInstance(){
        if(singletonInstance == null)
            singletonInstance = new SQLiteClientRepository();

        return singletonInstance;
    }

    @Override
    public void save(Client client) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = ClientContract.getContentValues(client);

        if(client.getId() == null) {
            db.insert(ClientContract.TABLE, null, values);
        }
        else {
            String where = ClientContract.ID + " = ?";
            String[] args = {client.getId().toString()};
            db.update(ClientContract.TABLE, values, where, args);
        }

        db.close();
        helper.close();
    }

    @Override
    public void delete(Client client) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        String where = ClientContract.ID + " = ?";
        String[] args = {client.getId().toString()};
        db.delete(ClientContract.TABLE, where, args);

        db.close();
        helper.close();
    }

    @Override
    public List<Client> getAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(ClientContract.TABLE, ClientContract.COLUMNS, null, null, null, null, ClientContract.NAME);
        List<Client> clients = ClientContract.bindList(cursor);

        db.close();
        helper.close();

        return clients;
    }
}
