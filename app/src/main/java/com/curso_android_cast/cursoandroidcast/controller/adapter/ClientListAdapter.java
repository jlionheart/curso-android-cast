package com.curso_android_cast.cursoandroidcast.controller.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.curso_android_cast.cursoandroidcast.R;
import com.curso_android_cast.cursoandroidcast.model.entity.Client;

import java.util.List;

public class ClientListAdapter extends BaseAdapter {

    private Activity context;
    private List<Client> clientList;

    public ClientListAdapter(Activity context, List<Client> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    public void setClientList(List<Client> clientList){
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Client getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(R.layout.adapter_client_list, parent, false);
        Client client = getItem(position);

        TextView textViewName;
        textViewName = (TextView)view.findViewById(R.id.textViewName);
        textViewName.setText(client.getName());

        TextView textViewAge = (TextView)view.findViewById(R.id.textViewAge);
        textViewAge.setText(client.getAge().toString());

        return view;
    }
}
