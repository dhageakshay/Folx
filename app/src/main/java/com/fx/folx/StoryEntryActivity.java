package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StoryEntryActivity extends AppCompatActivity {

    private User u;
    private EditText storyEntryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_entry);
        u = (User) getIntent().getSerializableExtra("New User" );

        storyEntryEditText = findViewById(R.id.storyEntryEditText);

        Button storyContinue = findViewById(R.id.storyContinue);

        storyContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String story = storyEntryEditText.getText().toString();
                if(!TextUtils.isEmpty(story)){
                    u.setStory(story);
                }else{

                    u.setStory("");
                }

                Intent i = new Intent(StoryEntryActivity.this,PictureEntryActivity.class);
                i.putExtra("New User",u);
                startActivity(i);
            }
        });

    }
}

