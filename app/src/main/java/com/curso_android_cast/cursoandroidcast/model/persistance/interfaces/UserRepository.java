package com.curso_android_cast.cursoandroidcast.model.persistance.interfaces;

import com.curso_android_cast.cursoandroidcast.model.entity.User;

public interface UserRepository {

    public void save(User user);

    public User.LoginAction login(User user);
}
