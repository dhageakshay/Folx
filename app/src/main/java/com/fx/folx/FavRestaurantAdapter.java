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

public class FavRestaurantAdapter extends RecyclerView.Adapter<FavRestaurantAdapter.FavRestaurantViewHolder> {

    private Context context;
    private List<Restaurant> favRestList;
    private TextView noFav;
    private ImageView badImg;

    public FavRestaurantAdapter(Context context, List favRestList, TextView noFav, ImageView badImg){
        this.context = context;
        this.favRestList = favRestList;
        this.badImg = badImg;
        this.noFav = noFav;
    }

    @NonNull
    @Override
    public FavRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new FavRestaurantAdapter.FavRestaurantViewHolder( LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_restaurant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavRestaurantViewHolder holder, int position) {
        holder.bind(favRestList.get(position));
    }

    @Override
    public int getItemCount() {
        return favRestList.size();
    }


    public class FavRestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView restName, restAddress;
        private ImageView restImage, deleteRest;

        public FavRestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restName = itemView.findViewById(R.id.favRestName);
            restAddress = itemView.findViewById(R.id.favRestAddress);
            restImage = itemView.findViewById(R.id.favRestImage);
            deleteRest = itemView.findViewById(R.id.favRestDelete);
        }

        public void bind(Restaurant restaurant) {
            restName.setText(restaurant.getName());
            restAddress.setText(restaurant.getLocation().getAddress());
            loadImage(restImage, restaurant.getThumb());
            deleteRest.setOnClickListener(this::onClick);
        }

        protected void loadImage(ImageView imageView, String imageUrl) {
            GlideApp.with(context)
                    .load(imageUrl)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            if(v.equals(deleteRest)) {
                favRestList.remove(getLayoutPosition());
                notifyItemRemoved(getLayoutPosition());
                notifyItemRangeChanged(getLayoutPosition(), favRestList.size());
                if(favRestList.isEmpty()){
                    noFav.setVisibility(View.VISIBLE);
                    badImg.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
