package com.curso_android_cast.cursoandroidcast.util.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    private ToastHelper() {
        super();
    }

    public static void showShortToast(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, int resId){
        Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
    }
}
