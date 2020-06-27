package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class SexualOrientationActivity extends AppCompatActivity {

    private ArrayList sexualOrientation;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexual_orientation);
        u= (User) getIntent().getSerializableExtra("New User");

        sexualOrientation = new ArrayList<>();

        final CheckBox male = findViewById(R.id.checkBoxMale);
        final CheckBox female = findViewById(R.id.checkBoxFemale);
        final CheckBox genderX = findViewById(R.id.checkBoxGenderX);

        Button orientationContinue = findViewById(R.id.orientationContinue);

        orientationContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (male.isChecked()){
                    sexualOrientation.add("Male");
                }
                if(female.isChecked()){
                    sexualOrientation.add("Female");
                }
                if(genderX.isChecked()) {
                    sexualOrientation.add("GenderX");
                }


                Intent i = new Intent(SexualOrientationActivity.this, ProfessionActivity.class);
                u.setSexualOrientation(sexualOrientation);
                i.putExtra("New User",u);
                startActivity(i);
            }
        });


    }
}
