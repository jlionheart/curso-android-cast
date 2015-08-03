package com.curso_android_cast.cursoandroidcast.controller.generic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.model.entity.User;
import com.curso_android_cast.cursoandroidcast.util.helper.ToastHelper;

public class LogOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!User.checkLogin())
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        bindLogOutMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void bindLogOutMenu(Menu menu){
        MenuItem item = menu.add(0, 0, forceLastMenuPosition(menu), R.string.menu_logout);
        item.setIcon(R.mipmap.ic_log_out);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener (new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick (MenuItem item){
                User loggedInUser = User.getLoggedInUser();

                if(loggedInUser != null){
                    loggedInUser.logout();
                    ToastHelper.showShortToast(LogOutActivity.this, R.string.action_user_logout);
                }
                return true;
            }
        });
    }

    private int forceLastMenuPosition(Menu menu){
        int position = 0;
        int itemOrder;

        for(int i = 0; i < menu.size(); i++){
            itemOrder = menu.getItem(i).getOrder();

            if(itemOrder > position)
                position = itemOrder;
        }

        return ++position;
    }
}
