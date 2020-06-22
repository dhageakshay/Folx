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

    private List<String> sexualOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sexual_orientation);

        sexualOrientation = new ArrayList<>();

        final CheckBox male = findViewById(R.id.checkBoxMale);
        final CheckBox female = findViewById(R.id.checkBoxFemale);
        final CheckBox genderX = findViewById(R.id.checkBoxGenderX);

        Button orinetationContinue = findViewById(R.id.orientationContinue);

        orinetationContinue.setOnClickListener(new View.OnClickListener() {
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
                startActivity(i);
            }
        });


    }
}
