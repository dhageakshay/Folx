package com.fx.folx;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackAdapter extends RecyclerView.Adapter<Stack> {


//    private List<Integer> items = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    private List<User> users;

    public StackAdapter(List<User> users){
        this.users = users;
    }

    @NonNull
    @Override
    public Stack onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Stack(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Stack holder, int position) {

        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void removeTopItem() {
        users.remove(0);
        notifyDataSetChanged();
    }
}
