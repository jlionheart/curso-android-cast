package com.curso_android_cast.cursoandroidcast.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.controller.adapter.ClientListAdapter;
import com.curso_android_cast.cursoandroidcast.model.entity.Client;
import com.curso_android_cast.cursoandroidcast.util.helper.ToastHelper;

import java.util.ArrayList;
import java.util.List;

public class ClientListActivity extends AppCompatActivity {

    private ListView listViewClients;
    private TextView textViewEmptyList;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        bindClientList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshClientList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionMenuRegister :
                Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_client_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionMenuEdit :
                Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
                intent.putExtra(ClientPersistActivity.CLIENT_PARAM, client);
                startActivity(intent);
                break;

            case R.id.actionMenuDelete :
                //client.delete();
                //ToastHelper.showShortToast(ClientListActivity.this, R.string.action_client_delete);
                //refreshClientList();
                new AlertDialog.Builder(ClientListActivity.this)
                .setMessage(R.string.message_delete_confirm_general).setTitle(R.string.message_delete_confirm_title_general)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        client.delete();
                        ToastHelper.showShortToast(ClientListActivity.this, R.string.action_client_delete);
                        refreshClientList();
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void bindClientList() {
        listViewClients = (ListView)findViewById(R.id.listViewClients);
        ClientListAdapter clientAdapter = new ClientListAdapter(ClientListActivity.this, Client.getAll());
        if(!showEmptyList(clientAdapter));
            listViewClients.setAdapter(clientAdapter);

        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false;
            }
        });

        registerForContextMenu(listViewClients);
    }

    private void refreshClientList() {
        ClientListAdapter clientAdapter = (ClientListAdapter)listViewClients.getAdapter();
        clientAdapter.setClientList(Client.getAll());

        if(!showEmptyList(clientAdapter));
            clientAdapter.notifyDataSetChanged();
    }

    private boolean showEmptyList(ClientListAdapter clientListAdapter){
        boolean showEmpty;
        textViewEmptyList = (TextView)findViewById(R.id.textViewEmptyList);

        if(clientListAdapter.getCount() <= 0){
            showEmpty = true;
            listViewClients.setVisibility(View.GONE);
            textViewEmptyList.setVisibility(View.VISIBLE);
        }
        else{
            showEmpty = false;
            textViewEmptyList.setVisibility(View.GONE);
            listViewClients.setVisibility(View.VISIBLE);
        }

        return showEmpty;
    }
}
