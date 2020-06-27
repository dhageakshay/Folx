package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfessionActivity extends AppCompatActivity {

    private EditText universityEditText, companyEditText, professionEditText;
    private User u;
    private Boolean isAllfilled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession);

        universityEditText = findViewById(R.id.univEditText);
        companyEditText = findViewById(R.id.companyEditText2);
        professionEditText = findViewById(R.id.professionEditText);
        Button professionContinue = findViewById(R.id.professionContinue);

        u = (User) getIntent().getSerializableExtra("New User");

        professionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllfilled = true;
                String university = universityEditText.getText().toString();
                String profession = professionEditText.getText().toString();
                String company = companyEditText.getText().toString();

                if(TextUtils.isEmpty(university)){
                    universityEditText.setError("Can't leave this field blank");
                    isAllfilled = false;
                }

                if(TextUtils.isEmpty(profession)){
                    professionEditText.setError("Can't leave this field blank");
                    isAllfilled = false;
                }

                if(TextUtils.isEmpty(company)){
                    companyEditText.setError("Can't leave this field blank");
                    isAllfilled = false;
                }
                if(isAllfilled){
                    Intent i = new Intent(ProfessionActivity.this,StoryEntryActivity.class);
                    u.setCompany(company);
                    u.setProfession(profession);
                    u.setUniversity(university);
                    i.putExtra("New User",u);
                    startActivity(i);
                }


            }
        });
    }
}
