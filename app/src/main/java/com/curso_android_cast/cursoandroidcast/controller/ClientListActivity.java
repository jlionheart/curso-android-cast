package com.curso_android_cast.cursoandroidcast.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.controller.adapter.ClientListAdapter;
import com.curso_android_cast.cursoandroidcast.controller.generic.LogOutActivity;
import com.curso_android_cast.cursoandroidcast.model.entity.Client;
import com.curso_android_cast.cursoandroidcast.util.helper.ToastHelper;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.protocol.HTTP;

public class ClientListActivity extends LogOutActivity {

    private ListView listViewClients;
    private TextView textViewEmptyList;
    private Client client;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        bindClientList();
        bindFab();
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
            case R.id.actionMenuShareClients :
                ClientListAdapter clientAdapter = (ClientListAdapter)listViewClients.getAdapter();

                if(clientAdapter.getCount() > 0) {
                    final Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, getShareContent(clientAdapter));
                    sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

                    // Create intent to show the chooser dialog
                    final Intent chooser = Intent.createChooser(sendIntent, getString(R.string.message_share_clients));

                    // Verify the original intent will resolve to at least one activity
                    if (sendIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(chooser);
                    }
                }
                else{
                    ToastHelper.showShortToast(ClientListActivity.this, R.string.label_no_client_registered);
                }
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
        textViewEmptyList = (TextView)findViewById(R.id.textViewEmptyList);

        ClientListAdapter clientAdapter = new ClientListAdapter(ClientListActivity.this, Client.getAll());
        listViewClients.setAdapter(clientAdapter);
        listViewClients.setEmptyView(textViewEmptyList);

        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client)parent.getItemAtPosition(position);
                return false;
            }
        });

        listViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getItemAtPosition(position);
                final Intent goToSOPhoneCall = new Intent(Intent.ACTION_DIAL); //or Intent.ACTION_CALL (no manifest permission needed)
                goToSOPhoneCall.setData(Uri.parse("tel:" + client.getPhone()));
                startActivity(goToSOPhoneCall);
            }
        });

        registerForContextMenu(listViewClients);
    }

    private void bindFab() {
        fabAdd = (FloatingActionButton)findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClientListActivity.this, ClientPersistActivity.class);
                startActivity(intent);
            }
        });
    }

    private void refreshClientList() {
        ClientListAdapter clientAdapter = (ClientListAdapter)listViewClients.getAdapter();
        clientAdapter.setClientList(Client.getAll());
        clientAdapter.notifyDataSetChanged();
    }

    private String getShareContent(ClientListAdapter clientListAdapter){
        StringBuilder shareContent = new StringBuilder();

        for(int i = 0; i < clientListAdapter.getCount(); i++){
            shareContent.append(clientListAdapter.getItem(i).getName() + " ");
        }

        return shareContent.toString();
    }
}
