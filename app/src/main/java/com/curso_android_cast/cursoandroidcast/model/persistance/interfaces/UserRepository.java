package com.curso_android_cast.cursoandroidcast.model.persistance.interfaces;

import com.curso_android_cast.cursoandroidcast.model.entity.User;

import java.util.List;

public interface UserRepository {

    public void save(User user);

    public boolean login(User user);
}
