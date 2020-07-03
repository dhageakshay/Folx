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

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantItemList;
    private Context context;

    public RestaurantAdapter( Context context, List restaurantItemList){
        this.restaurantItemList = restaurantItemList;
        this.context = context;
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


    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private TextView restName, restAddress;
        private ImageView restImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restName = itemView.findViewById(R.id.restName);
            restAddress = itemView.findViewById(R.id.restAddress);
            restImage = itemView.findViewById(R.id.restImage);
        }

        public void bind(Restaurant restaurant) {
            restName.setText(restaurant.getName());
            restAddress.setText(restaurant.getLocation().getAddress());
            loadImage(restImage, restaurant.getThumb());
        }

        protected void loadImage(ImageView imageView, String imageUrl) {
            GlideApp.with(context)
                    .load(imageUrl)
                    .into(imageView);
        }
    }
}
