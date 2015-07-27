package com.curso_android_cast.cursoandroidcast.model.persistance.interfaces;

import com.curso_android_cast.cursoandroidcast.model.entity.Client;

import java.util.List;

public interface ClientRepository {

    public void save(Client client);

    public List<Client> getAll();

    public void delete(Client client);
}
