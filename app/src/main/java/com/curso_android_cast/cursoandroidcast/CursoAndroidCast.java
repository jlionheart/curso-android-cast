package com.curso_android_cast.cursoandroidcast;

import android.app.Application;

import com.curso_android_cast.cursoandroidcast.util.AppUtil;

public class CursoAndroidCast extends Application {

    @Override
    public void onCreate() {
        AppUtil.CONTEXT = getApplicationContext();
        super.onCreate();
    }
}
