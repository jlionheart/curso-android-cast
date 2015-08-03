package com.curso_android_cast.cursoandroidcast.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.model.entity.User;
import com.curso_android_cast.cursoandroidcast.util.helper.FormHelper;
import com.curso_android_cast.cursoandroidcast.util.helper.ToastHelper;


public class LoginActivity extends AppCompatActivity {
    private User user;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindFields();

        if(User.getLoggedInUser() != null)
            redirectToListActivity();
    }

    private void bindUser(){
        user = new User();
        user.setUserName(editTextUserName.getText().toString());
        user.setPassword(editTextPassword.getText().toString());
    }

    private void bindFields(){
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        bindLoginButton();
    }

    private void bindLoginButton(){
        loginButton = (Button)findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FormHelper.requireValidate(LoginActivity.this, editTextUserName, editTextPassword)) {
                    bindUser();
                    User.LoginAction loginAction = user.login();

                    switch (loginAction){
                        case SUCCESS:
                            redirectToListActivity();
                            break;

                        case INVALID_PASSWORD:
                            ToastHelper.showShortToast(LoginActivity.this, R.string.error_login_invalid_password);
                            break;

                        default:
                            ToastHelper.showShortToast(LoginActivity.this, R.string.error_login_user_do_not_exists);
                            break;
                    }
                }
            }
        });
    }

    private void redirectToListActivity() {
        Intent intent = new Intent(LoginActivity.this, ClientListActivity.class);
        startActivity(intent);
        finish();
    }
}
