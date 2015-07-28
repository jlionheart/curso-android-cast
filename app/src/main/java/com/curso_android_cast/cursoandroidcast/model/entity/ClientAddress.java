package com.curso_android_cast.cursoandroidcast.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientAddress {

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
}
