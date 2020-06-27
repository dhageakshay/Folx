package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class GenderActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private User u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);
        radioGroup =  findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        u  = (User) getIntent().getSerializableExtra("New User");
        final String gender;



        Button genderContinue = findViewById(R.id.genderContinue);
        genderContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectId = radioGroup.getCheckedRadioButtonId();

                RadioButton rb = findViewById(selectId);

                if(rb!=null){
                    u.setGender(rb.getText().toString());
                    Intent i = new Intent(GenderActivity.this, SexualOrientationActivity.class);
                    i.putExtra("New User", u);
                    startActivity(i);
                }
                else{
                    Snackbar.make(v, "Please select your gender", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }



}
