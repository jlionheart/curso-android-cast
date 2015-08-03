package com.curso_android_cast.cursoandroidcast.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.curso_android_cast.cursoandroidcast.model.entity.base.BaseEntity;
import com.curso_android_cast.cursoandroidcast.model.persistance.SQLiteUserRepository;
import com.curso_android_cast.cursoandroidcast.model.persistance.session.UserSessionManager;

public class User extends BaseEntity implements Parcelable {

    private String name;
    private String userName;
    private String password;
    private String salt;

    public enum LoginAction {
        SUCCESS, INVALID_PASSWORD, USER_DO_NOT_EXISTS;
    }

    public User(){
        super();
    }

    public User(Parcel in){
        super();
        readToParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void save(){
        SQLiteUserRepository.getInstance().save(this);
    }

    public static User getLoggedInUser(){
        return UserSessionManager.getInstance().getLoggedInUser();
    }

    public static boolean checkLogin(){
        return UserSessionManager.getInstance().checkLogin();
    }

    public void createLoginSession(){
        UserSessionManager.getInstance().createLoginSession(this);
    }

    public LoginAction login(){
        return SQLiteUserRepository.getInstance().login(this);
    }

    public void logout(){
        UserSessionManager.getInstance().logoutUser();
    }

    private void readToParcel(Parcel in){
        int partialId = in.readInt();
        setId(partialId == -1 ? null : partialId);
        name = in.readString();
        userName = in.readString();
        password = in.readString();
        salt = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source){
            return new User(source);
        }

        public User[] newArray(int size){
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId() == null ? -1 : getId());
        dest.writeString(name == null ? "" : name);
        dest.writeString(userName == null ? "" : userName);
        dest.writeString(password == null ? "" : password);
        dest.writeString(salt == null ? "" : salt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null)
            return false;
        if (password != null ? !password.equals(user.password) : user.password != null)
            return false;
        return !(salt != null ? !salt.equals(user.salt) : user.salt != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        return result;
    }
}
