package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class StoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        final EditText storyEdit = findViewById(R.id.storyEditText);
        final TextView storyText = findViewById(R.id.storyText);

        final FloatingActionButton editStoryButton = findViewById(R.id.editStory);
        final FloatingActionButton saveStoryButton = findViewById(R.id.doneStory);

        final String story = (String) storyText.getText();

        editStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editStoryButton.setVisibility(View.GONE);
                storyText.setVisibility(View.GONE);
                saveStoryButton.setVisibility(View.VISIBLE);
                storyEdit.setVisibility(View.VISIBLE);

                storyEdit.setText(story);

            }
        });

        saveStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable storyUpdated = storyEdit.getText();
                storyText.setText(storyUpdated);

                //Update the story to the sever

                editStoryButton.setVisibility(View.VISIBLE);
                storyText.setVisibility(View.VISIBLE);
                saveStoryButton.setVisibility(View.GONE);
                storyEdit.setVisibility(View.GONE);
            }
        });
    }
}
