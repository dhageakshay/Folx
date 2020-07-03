package com.fx.folx.api;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("address")
    private String address;
    @SerializedName("locality")
    private String locality;
    @SerializedName("city")
    private String city;
    @SerializedName("city_id")
    private Integer city_id;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("country_id")
    private Integer country_id;
    @SerializedName("locality_verbose")
    private String locality_verbose;

    public Location(String address, String locality, String city, Integer city_id, String latitude, String longitude, String zipcode, Integer country_id, String locality_verbose) {
        this.address = address;
        this.locality = locality;
        this.city = city;
        this.city_id = city_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipcode = zipcode;
        this.country_id = country_id;
        this.locality_verbose = locality_verbose;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public Integer getCity_id() {
        return city_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public String getLocality_verbose() {
        return locality_verbose;
    }

}
