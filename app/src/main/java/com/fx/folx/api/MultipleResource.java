package com.fx.folx.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MultipleResource {

    @SerializedName("results_found")
    private Integer results_found;
    @SerializedName("results_start")
    private Integer results_start;
    @SerializedName("results_shown")
    private Integer results_shown;
    @SerializedName("restaurants")
    private List<RestaurantItem> restaurants;

    public class RestaurantItem{
        @SerializedName("restaurant")
        private Restaurant restaurant;

        public RestaurantItem(Restaurant restaurant) {
            this.restaurant = restaurant;
        }

        public Restaurant getRestaurant() {
            return restaurant;
        }
    }


    public MultipleResource(Integer results_found, Integer results_start, Integer results_shown, List<RestaurantItem> restaurants) {
        this.results_found = results_found;
        this.results_start = results_start;
        this.results_shown = results_shown;
        this.restaurants = restaurants;
    }

    public Integer getResults_found() {
        return results_found;
    }

    public Integer getResults_start() {
        return results_start;
    }

    public Integer getResults_shown() {
        return results_shown;
    }

    public List<RestaurantItem> getRestaurants() {
        return restaurants;
    }
}