package com.curso_android_cast.cursoandroidcast.model.service;

import com.curso_android_cast.cursoandroidcast.model.entity.ClientAddress;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public final class CepService {

    private static final String SERVICE_URL = "http://correiosapi.apphb.com/cep/";

    private CepService(){
        super();
    }

    public static ClientAddress getAddressByZipCode(String zipCode){
        ClientAddress address = null;

        try {
            URL url = new URL(SERVICE_URL + zipCode);
            final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();

            if(responseCode != HttpURLConnection.HTTP_OK)
                return null;

            InputStream inputStream = connection.getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            address = (ClientAddress)objectMapper.readValue(inputStream, ClientAddress.class);

            connection.disconnect();

        }
        catch (IOException e){
            e.printStackTrace();
        }

        return address;
    }

}
