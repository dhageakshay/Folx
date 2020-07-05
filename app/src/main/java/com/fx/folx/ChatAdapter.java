package com.fx.folx;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    List<User> list;
    Context context;


    public ChatAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        ChatViewHolder holder = new ChatViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, int position) {

        holder.senderName.setText(list.get(position).getName());
        //holder.imageView.setImageResource();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(),MessageListActivity.class);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, User data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(User data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {


        TextView senderName;
        ImageView imageView;

        public ChatViewHolder(View itemView) {
            super(itemView);
            senderName = itemView.findViewById(R.id.senderName);

            imageView = itemView.findViewById(R.id.chatdp);
        }
/*
        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(),MessageListActivity.class);
            context.startActivity(i);
        }*/
    }

}
