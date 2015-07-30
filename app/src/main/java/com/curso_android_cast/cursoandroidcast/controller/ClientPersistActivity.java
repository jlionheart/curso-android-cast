package com.curso_android_cast.cursoandroidcast.controller;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.model.entity.Client;
import com.curso_android_cast.cursoandroidcast.model.entity.ClientAddress;
import com.curso_android_cast.cursoandroidcast.model.service.CepService;
import com.curso_android_cast.cursoandroidcast.util.helper.FormHelper;
import com.curso_android_cast.cursoandroidcast.util.helper.ToastHelper;

public class ClientPersistActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextPhone;
    private EditText editTextAddress;
    private EditText editTextZipCode;
    private EditText editTextAddressType;
    private EditText editTextDistrict;
    private EditText editTextCity;
    private EditText editTextProvince;
    private Button buttonFindZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_persist);
        bindFields();
        getParameters();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;

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
        client.getAddress().setAddress(editTextAddress.getText().toString());
        client.getAddress().setAddressType(editTextAddressType.getText().toString());
        client.getAddress().setDistrict(editTextDistrict.getText().toString());
        client.getAddress().setCity(editTextCity.getText().toString());
        client.getAddress().setZipCode(editTextZipCode.getText().toString());
        client.getAddress().setProvince(editTextProvince.getText().toString());
    }

    private void fillForm(Client client){
        editTextName.setText(client.getName());
        editTextAge.setText(client.getAge().toString());
        editTextPhone.setText(client.getPhone());
        editTextAddress.setText(client.getAddress().getAddress());
        fillAddressForm(client.getAddress());
    }

    private void fillAddressForm(ClientAddress clientAddress){
        editTextAddress.setText(clientAddress.getAddress());
        editTextAddressType.setText(clientAddress.getAddressType());
        editTextDistrict.setText(clientAddress.getDistrict());
        editTextCity.setText(clientAddress.getCity());
        editTextZipCode.setText(clientAddress.getZipCode());
        editTextProvince.setText(clientAddress.getProvince());
    }

    private void getParameters() {
        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            client = extras.getParcelable(CLIENT_PARAM);

            if(client == null)
                throw new IllegalArgumentException();
            else
                fillForm(client);
        }
    }

    private void bindFields() {
        editTextName = (EditText)findViewById(R.id.editTextName);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextPhone = (EditText)findViewById(R.id.editTextPhone);
        editTextAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextZipCode = (EditText)findViewById(R.id.editTextZipCode);
        editTextAddressType = (EditText)findViewById(R.id.editTextAddressType);
        editTextDistrict = (EditText)findViewById(R.id.editTextDistrict);
        editTextCity = (EditText)findViewById(R.id.editTextCity);
        editTextProvince = (EditText)findViewById(R.id.editTextProvince);
        bindButtonFindZipCode();

        editTextName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextName.getRight() - editTextName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });
    }

    /**
     * @see <a href="http://developer.android.com/training/basics/intents/result.html">Getting a Result from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                            ContactsContract.CommonDataKinds.StructuredPostal.STREET,
                            ContactsContract.CommonDataKinds.StructuredPostal.REGION,
                            ContactsContract.CommonDataKinds.StructuredPostal.CITY,
                            ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    editTextName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    editTextPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    editTextAddress.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                    editTextProvince.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                    editTextCity.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
                    editTextZipCode.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void bindButtonFindZipCode() {
        buttonFindZipCode = (Button)findViewById(R.id.buttonFindZipCode);
        buttonFindZipCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FormHelper.requireValidate(ClientPersistActivity.this, editTextZipCode)) {
                    new GetAddressByCep().execute(editTextZipCode.getText().toString());
                }
            }
        });
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ClientPersistActivity.this);
            progressDialog.setTitle(getString(R.string.loader_zip_code_title));
            progressDialog.setMessage(getString(R.string.loader_zip_code_message));
            progressDialog.show();
        }

        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddressByZipCode(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            super.onPostExecute(clientAddress);

            if(clientAddress == null) {
                ToastHelper.showShortToast(ClientPersistActivity.this, R.string.error_no_zip_code_found);
            }
            else {
                fillAddressForm(clientAddress);
            }

            progressDialog.dismiss();
        }
    }
}
