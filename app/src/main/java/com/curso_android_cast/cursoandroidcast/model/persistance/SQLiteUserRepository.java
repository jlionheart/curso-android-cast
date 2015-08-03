package com.curso_android_cast.cursoandroidcast.model.persistance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.curso_android_cast.cursoandroidcast.model.entity.User;
import com.curso_android_cast.cursoandroidcast.model.persistance.contract.UserContract;
import com.curso_android_cast.cursoandroidcast.model.persistance.helper.DataBaseHelper;
import com.curso_android_cast.cursoandroidcast.model.persistance.interfaces.UserRepository;
import com.curso_android_cast.cursoandroidcast.util.AppUtil;
import com.curso_android_cast.cursoandroidcast.util.PasswordUtil;


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
    }

    @Override
    public User.LoginAction login(User user){
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        User.LoginAction loginAction = User.LoginAction.USER_DO_NOT_EXISTS;

        String where = UserContract.USER_NAME + " = ?";
        String[] args = { user.getUserName() };
        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, where, args, null, null, null);

        User dataBaseUser = UserContract.bind(cursor);

        db.close();
        helper.close();

        if(dataBaseUser != null){
            String loginPassword = PasswordUtil.generatePasswordHash(user.getPassword(), dataBaseUser.getSalt());

            if(loginPassword.equals(dataBaseUser.getPassword())) {
                loginAction = User.LoginAction.SUCCESS;
                dataBaseUser.createLoginSession();
            }
            else
                loginAction = User.LoginAction.INVALID_PASSWORD;
        }

        return loginAction;
    }
}
