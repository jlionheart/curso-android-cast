package com.curso_android_cast.cursoandroidcast.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.model.entity.Client;
import com.curso_android_cast.cursoandroidcast.util.helper.FormHelper;
import com.curso_android_cast.cursoandroidcast.util.helper.ToastHelper;

public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_persist);

        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            client = extras.getParcelable(CLIENT_PARAM);

            if(client == null)
                throw new IllegalArgumentException();
            else
                bindForm(client);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionMenuSave :
                if(FormHelper.requireValidate(ClientPersistActivity.this, editTextName, editTextPhone, editTextAge, editTextAddress)){
                    bindClient();
                    int message = client.getId() == null ? R.string.action_client_register_success : R.string.action_client_update_success;
                    client.save();
                    ToastHelper.showShortToast(ClientPersistActivity.this, message);
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindClient(){
        if(client == null || client.getId() == null)
            client = new Client();

        client.setName(editTextName.getText().toString());
        client.setAge(Integer.valueOf(editTextAge.getText().toString()));
        client.setPhone(editTextPhone.getText().toString());
        client.setAddress(editTextAddress.getText().toString());
    }

    private void bindForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhone.setText(client.getPhone());
        editTextAddress.setText(client.getAddress());
    }
}
