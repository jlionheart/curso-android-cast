package com.curso_android_cast.cursoandroidcast.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientAddress implements Parcelable {

    @JsonProperty("logradouro")
    private String address;

    @JsonProperty("tipoDeLogradouro")
    private String addressType;

    @JsonProperty("bairro")
    private String district;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("cep")
    private String zipCode;

    @JsonProperty("estado")
    private String province;

    public ClientAddress(){
        super();
    }

    public ClientAddress(Parcel in){
        super();
        readToParcel(in);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    private void readToParcel(Parcel in) {
        address = in.readString();
        addressType = in.readString();
        district = in.readString();
        city = in.readString();
        zipCode = in.readString();
        province = in.readString();
    }

    public static final Parcelable.Creator<ClientAddress> CREATOR = new Parcelable.Creator<ClientAddress>() {
        public ClientAddress createFromParcel(Parcel source){
            return new ClientAddress(source);
        }

        public ClientAddress[] newArray(int size){
            return new ClientAddress[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address == null ? "" : address);
        dest.writeString(addressType == null ? "" : addressType);
        dest.writeString(district == null ? "" : district);
        dest.writeString(city == null ? "" : city);
        dest.writeString(zipCode == null ? "" : zipCode);
        dest.writeString(province == null ? "" : province);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientAddress that = (ClientAddress) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (addressType != null ? !addressType.equals(that.addressType) : that.addressType != null)
            return false;
        if (district != null ? !district.equals(that.district) : that.district != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (zipCode != null ? !zipCode.equals(that.zipCode) : that.zipCode != null) return false;
        return !(province != null ? !province.equals(that.province) : that.province != null);

    }

    @Override
    public int hashCode() {
        int result = address != null ? address.hashCode() : 0;
        result = 31 * result + (addressType != null ? addressType.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        return result;
    }
}
