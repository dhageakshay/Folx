package com.fx.folx;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Stack extends RecyclerView.ViewHolder {

    TextView nameView, ageView;

    public Stack(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.feedName);
        ageView = itemView.findViewById(R.id.feedAge);
    }


    public void bind(User u) {
        nameView.setText(u.getUserName());
        ageView.setText(String.valueOf(u.getUserAge()));
    }
}
