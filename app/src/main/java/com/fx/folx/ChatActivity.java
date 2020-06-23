package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        List<User> conversations = fetchConversations();

        RecyclerView recyclerView = findViewById(R.id.reyclerview_chat_list);
        ChatAdapter adapter = new ChatAdapter(conversations, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




    }

    private List<User> fetchConversations(){

        List<User> conversation = new ArrayList<>();

        conversation.add(new User("Anna",22));
        conversation.add(new User("Mary",28));
        conversation.add(new User("Sophia",26));
        conversation.add(new User("Jane",32));
        conversation.add(new User("Emma",36));


        return  conversation;
    }
}
