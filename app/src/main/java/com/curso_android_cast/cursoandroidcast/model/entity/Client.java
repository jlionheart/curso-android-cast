package com.curso_android_cast.cursoandroidcast.model.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.curso_android_cast.cursoandroidcast.model.entity.base.BaseEntity;
import com.curso_android_cast.cursoandroidcast.model.persistance.SQLiteClientRepository;

import java.util.List;

public class Client extends BaseEntity implements Parcelable {

    private String name;
    private Integer age;
    private String phone;
    private ClientAddress address;

    public Client() {
        super();
        address = new ClientAddress();
    }

    public Client(Parcel in){
        super();
        readToParcel(in);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClientAddress getAddress() {
        return address;
    }

    public void readToParcel(Parcel in){
        int partialId = in.readInt();
        setId(partialId == -1 ? null : partialId);
        name = in.readString();
        int partialAge = in.readInt();
        age = partialAge == -1 ? null : partialAge;
        phone = in.readString();
        address = in.readParcelable(ClientAddress.class.getClassLoader());
    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
        public Client createFromParcel(Parcel source){
            return new Client(source);
        }

        public Client[] newArray(int size){
            return new Client[size];
        }
    };

    public void save(){
        SQLiteClientRepository.getInstance().save(this);
    }

    public void delete(){
        SQLiteClientRepository.getInstance().delete(this);
    }

    public static List<Client> getAll(){
        return SQLiteClientRepository.getInstance().getAll();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId() == null ? -1 : getId());
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age);
        dest.writeString(phone == null ? "" : phone);
        dest.writeParcelable(address, flags);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (age != null ? !age.equals(client.age) : client.age != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        return !(address != null ? !address.equals(client.address) : client.address != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
