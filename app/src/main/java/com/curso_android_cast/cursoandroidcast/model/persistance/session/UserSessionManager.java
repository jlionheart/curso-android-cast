package com.curso_android_cast.cursoandroidcast.model.persistance.session;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.curso_android_cast.cursoandroidcast.controller.LoginActivity;
import com.curso_android_cast.cursoandroidcast.model.entity.User;
import com.curso_android_cast.cursoandroidcast.model.persistance.interfaces.UserSession;
import com.curso_android_cast.cursoandroidcast.util.AppUtil;


public class UserSessionManager implements UserSession {

    private static UserSessionManager singletonInstance;
    private static final String PREF_NAME = "UserSessionPref";
    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String USER_NAME = "userName";
    private static final int PRIVATE_MODE = 0;

    private SharedPreferences pref;
    private Editor editor;

    private UserSessionManager(){
        super();
        pref = AppUtil.CONTEXT.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static UserSession getInstance(){
        if(singletonInstance == null)
            singletonInstance = new UserSessionManager();

        return singletonInstance;
    }

    @Override
    public void createLoginSession(User user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_NAME, user.getName());
        editor.putString(USER_NAME, user.getUserName());
        editor.commit();
    }

    @Override
    public boolean checkLogin(){
        boolean isLoggedIn = isLoggedIn();

        if(!isLoggedIn){
            Intent intent = new Intent(AppUtil.CONTEXT, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            AppUtil.CONTEXT.startActivity(intent);
        }

        return isLoggedIn;
    }

    private boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    @Override
    public User getLoggedInUser(){
        if(isLoggedIn()) {
            User user = new User();
            user.setName(pref.getString(KEY_NAME, null));
            user.setUserName(pref.getString(USER_NAME, null));

            return user;
        };

        return null;
    }

    @Override
    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(AppUtil.CONTEXT, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        AppUtil.CONTEXT.startActivity(intent);
    }
}
