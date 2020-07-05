package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fx.folx.api.APIClient;
import com.fx.folx.api.APIInterface;
import com.fx.folx.api.MultipleResource;
import com.fx.folx.api.Restaurant;
import com.google.android.material.snackbar.Snackbar;

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
    private APIInterface apiInterface;

    private User u;
    //TODO: Change the apikey to company registered key
    String apiKey = "f5563e2d416bf5ff9a25dc6b249185b4";

    private RecyclerView restaurantRecyclerView,favRestaurantRecyclerView;
    private TextView noFavText;
    private ImageView badImage;
    private List<Restaurant> restaurantList;
    private RestaurantAdapter adapter;
    protected ArrayList<Restaurant> favList;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_entry);
        restaurantRecyclerView = findViewById(R.id.restaurantRecyclerView);
        favRestaurantRecyclerView = findViewById(R.id.favRestaurantRecyclerView);
        noFavText = findViewById(R.id.noFavText);
        badImage = findViewById(R.id.badImage);
        Button continueButton = findViewById(R.id.restContinue);
        searchView = findViewById(R.id.restSearchView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        restaurantRecyclerView.setLayoutManager(llm);
        LinearLayoutManager llm1 = new LinearLayoutManager(this);
        llm1.setOrientation(LinearLayoutManager.VERTICAL);
        favRestaurantRecyclerView.setLayoutManager(llm1);

        restaurantList = new ArrayList<>();
        favList = new ArrayList<>();

        u = (User) getIntent().getSerializableExtra("New User" );

        //TODO: Fix redundancy of restaurants in favorites
        FavRestaurantAdapter favRestaurantAdapter = new FavRestaurantAdapter(this,favList,noFavText,badImage);
        favRestaurantRecyclerView.setAdapter(favRestaurantAdapter);

        //Fetch the data by calling the API
        apiInterface = APIClient.getClient();

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

                adapter = new RestaurantAdapter(getApplicationContext(),restaurantList,favList,favRestaurantRecyclerView,
                                                favRestaurantAdapter,noFavText, badImage);
                restaurantRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                Toast.makeText(RestaurantEntryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        continueButton.setOnClickListener(v -> {
            if(favList.isEmpty()){
                Snackbar.make(v,"Please select at least one favorite restaurant",Snackbar.LENGTH_SHORT).show();
            }
            else{
                    u.setFavRestaurantList(favList);
                    Intent i = new Intent(RestaurantEntryActivity.this,PictureEntryActivity.class);
                    i.putExtra("New User",u);
                    startActivity(i);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Call<MultipleResource> call = apiInterface.getRestaurantBySearch(entityId,entityType,query,apiKey);

                call.enqueue(new Callback<MultipleResource>() {
                    @Override
                    public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {
                        restaurantList.clear();
                        MultipleResource resource = response.body();
                        Log.d(TAG," "+resource.getRestaurants().size());
                        List<MultipleResource.RestaurantItem> restaurantItemsList = resource.getRestaurants();
                        for(MultipleResource.RestaurantItem r: restaurantItemsList){
                            Log.d(TAG," "+r.getRestaurant().getName());
                            restaurantList.add(r.getRestaurant());
                        }

                        adapter = new RestaurantAdapter(getApplicationContext(),restaurantList,favList,favRestaurantRecyclerView,
                                favRestaurantAdapter,noFavText, badImage);
                        restaurantRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<MultipleResource> call, Throwable t) {
                        Toast.makeText(RestaurantEntryActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
