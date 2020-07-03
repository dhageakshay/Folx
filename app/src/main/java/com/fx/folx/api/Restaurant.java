package com.fx.folx.api;

import com.google.gson.annotations.SerializedName;

public class Restaurant {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("location")
    private Location location;
    @SerializedName("thumb")
    private String thumb;

    public Restaurant(String id, String name, String url, Location location, String thumb) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.location = location;
        this.thumb = thumb;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Location getLocation() {
        return location;
    }

    public String getThumb() {
        return thumb;
    }
}
