package com.fx.folx;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fx.folx.ui.glide.GlideApp;

public class Stack extends RecyclerView.ViewHolder {

    TextView nameView, ageView;
    ImageView img;

    public Stack(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.feedName);
        ageView = itemView.findViewById(R.id.feedAge);
        img = itemView.findViewById(R.id.feedimage);

    }


    public void bind(User u) {
        Log.d("STACK","Setting  up feed");
        nameView.setText(u.getName());
        ageView.setText(String.valueOf(u.calcUserAge()));
        GlideApp.with(img).load(u.getImageList().get(0)).into(img);
    }
}
