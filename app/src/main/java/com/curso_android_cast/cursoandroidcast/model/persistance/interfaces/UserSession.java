package com.curso_android_cast.cursoandroidcast.model.persistance.interfaces;

import com.curso_android_cast.cursoandroidcast.model.entity.User;

public interface UserSession {

    public void createLoginSession(User user);

    public boolean checkLogin();

    public User getLoggedInUser();

    public void logoutUser();
}
