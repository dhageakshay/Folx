package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfessionActivity extends AppCompatActivity {

    private EditText university, company, profession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession);

        university = findViewById(R.id.univEditText);
        company = findViewById(R.id.companyEditText2);
        profession = findViewById(R.id.professionEditText);

        Button professionContinue = findViewById(R.id.professionContinue);

        professionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfessionActivity.this,StoryEntryActivity.class));
            }
        });
    }
}
