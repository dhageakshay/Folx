package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fx.folx.ui.main.SignInFragment;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        TextView welcome = findViewById(R.id.welcome);
        welcome.setTypeface(Typeface.createFromAsset(getAssets(),"Montserrat-Medium.ttf"));

        Button signUp = findViewById(R.id.signup);
        Button signIn = findViewById(R.id.signin);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnBoardingActivity.this, SignIn.class);
                i.putExtra("viewpager_pos",0);
                startActivity(i);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OnBoardingActivity.this, SignIn.class);
                i.putExtra("viewpager_pos",1);
                startActivity(i);
            }
        });
    }
}
