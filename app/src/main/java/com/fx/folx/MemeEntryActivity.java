package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MemeEntryActivity extends AppCompatActivity {

    private ImageView imageView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_entry);

        Button imgContinue = findViewById(R.id.memeContinue);


        imgContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("accounts");
                FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

                myRef.child(fUser.getUid()).setValue(user);

//                Log.d("MEMEENTRY",fUser.getUid());
//                Intent i = new Intent(MemeEntryActivity.this, SwipeActivity.class);
//                startActivity(i);
            }
        });
    }
}
