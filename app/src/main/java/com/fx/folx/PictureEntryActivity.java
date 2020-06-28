package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PictureEntryActivity extends AppCompatActivity {

    private ImageView imageView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_entry);

        Button imgContinue = findViewById(R.id.imgContinue);
        user = (User) getIntent().getSerializableExtra("New User");



        imgContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PictureEntryActivity.this, MemeEntryActivity.class);
                i.putExtra("New User",user);
                startActivity(i);
            }
        });
    }
}
