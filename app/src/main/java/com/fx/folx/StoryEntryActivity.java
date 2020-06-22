package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StoryEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_entry);

        EditText storyEntryEditText = findViewById(R.id.storyEntryEditText);

        Button storyContinue = findViewById(R.id.storyContinue);

        storyContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoryEntryActivity.this, PictureEntryActivity.class));
            }
        });

    }
}

