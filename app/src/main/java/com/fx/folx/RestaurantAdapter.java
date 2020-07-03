package com.fx.folx;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fx.folx.api.Restaurant;
import com.fx.folx.ui.glide.GlideApp;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantItemList;
    private List<Restaurant> favRestList;
    private Context context;
    private RecyclerView favView;
    private FavRestaurantAdapter adapter;
    private TextView noFav;
    private ImageView badImg;

    public RestaurantAdapter(Context context, List<Restaurant> restaurantItemList, List<Restaurant> favRestList,
                             RecyclerView favView, FavRestaurantAdapter adapter, TextView noFav, ImageView badImg){
        this.restaurantItemList = restaurantItemList;
        this.context = context;
        this.favRestList = favRestList;
        this.favView = favView;
        this.adapter= adapter;
        this.badImg = badImg;
        this.noFav = noFav;
        favView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
            holder.bind(restaurantItemList.get(position));
    }


    @Override
    public int getItemCount() {
        return restaurantItemList.size();
    }


    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView restName, restAddress;
        private ImageView restImage;
        private Restaurant restaurant;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this::onClick);
            restName = itemView.findViewById(R.id.restName);
            restAddress = itemView.findViewById(R.id.restAddress);
            restImage = itemView.findViewById(R.id.restImage);
        }

        public void bind(Restaurant restaurant) {
            this.restaurant = restaurant;
            restName.setText(restaurant.getName());
            restAddress.setText(restaurant.getLocation().getAddress());
            loadImage(restImage, restaurant.getThumb());
        }

        protected void loadImage(ImageView imageView, String imageUrl) {
            GlideApp.with(context)
                    .load(imageUrl)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            if(favRestList.size() < 3){
                noFav.setVisibility(View.GONE);
                badImg.setVisibility(View.GONE);
                favRestList.add(restaurant);
                adapter.notifyItemInserted(favRestList.indexOf(restaurant));
            }
            else {
                Snackbar.make(v,"You've selected three restaurants already. Please delete at least one of them and try again",Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
