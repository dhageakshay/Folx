package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fx.folx.api.APIClient;
import com.fx.folx.api.APIInterface;
import com.fx.folx.api.MultipleResource;
import com.fx.folx.api.Restaurant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.fx.folx.api.APIInterface.BASE_URL;

public class RestaurantEntryActivity extends AppCompatActivity {


    private final String TAG="RESTAURANTENTRYACTIVITY";
    /*
    entity_id is 4 for Bangalore
    Alternatively, you can fetch the city_id from the api based on user's location and then pass the fetched city ids to fetch restaurants in that particular city
     */
    private String entityId = "4";
    private String entityType = "city";
    private String searchRestaurant;

    //TODO: Change the apikey to company registered key
    String apiKey = "f5563e2d416bf5ff9a25dc6b249185b4";

    private RecyclerView restaurantRecyclerView;
    private List<Restaurant> restaurantList;
    private RestaurantAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_entry);
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        restaurantRecyclerView.setLayoutManager(llm);
        restaurantList = new ArrayList<>();

        //Fetch the data by calling the API
        APIInterface apiInterface = APIClient.getClient();

        Call<MultipleResource> call = apiInterface.getRestaurantBySearch(entityId,entityType,apiKey);

        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {
                MultipleResource resource = response.body();
                Log.d(TAG," "+resource.getRestaurants().size());
                List<MultipleResource.RestaurantItem> restaurantItemsList = resource.getRestaurants();
                for(MultipleResource.RestaurantItem r: restaurantItemsList){
                    Log.d(TAG," "+r.getRestaurant().getName());
                    restaurantList.add(r.getRestaurant());
                }

               adapter = new RestaurantAdapter(getApplicationContext(),restaurantList);
                restaurantRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                Toast.makeText(RestaurantEntryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
