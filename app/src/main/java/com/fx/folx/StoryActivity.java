package com.fx.folx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoryActivity extends AppCompatActivity {

    private String story;
    private final String TAG = "STORYACTIVITY";

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        final EditText storyEdit = findViewById(R.id.storyEditText);
        final TextView storyText = findViewById(R.id.storyText);

        final FloatingActionButton editStoryButton = findViewById(R.id.editStory);
        final FloatingActionButton saveStoryButton = findViewById(R.id.doneStory);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userKey = user.getUid();
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("accounts");

        ref.child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userID = snapshot.child("email").getValue(String.class);
                story = snapshot.child("story").getValue(String.class);
                Log.d(TAG, "Name: " + userID);
                if(!story.equals("")){
                    storyText.setText(story);
                }
                else {
                    Snackbar.make(storyText,"Story isn't updated",Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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

                DatabaseReference ref = database.getReference("accounts");
                ref.child(user.getUid()).child("story").setValue(storyText.getText().toString());
                Snackbar.make(v,"Story Updated",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
