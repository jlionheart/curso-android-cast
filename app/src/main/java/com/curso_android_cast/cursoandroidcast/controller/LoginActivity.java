package com.curso_android_cast.cursoandroidcast.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.curso_android_cast.cursoandroidcast.R;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindLoginButton();
    }

    protected void bindLoginButton(){
        loginButton = (Button)findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, ClientListActivity.class);
            startActivity(intent);
            }
        });
    }
}
