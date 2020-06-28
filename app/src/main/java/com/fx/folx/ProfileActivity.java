package com.fx.folx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ProfileActivity extends AppCompatActivity {

    private TextView moreText;
    private TextView nameText,professionText,companyText,universityText,ageText;
    private Switch maleSwitch,femaleSwitch,genderXSwitch;
    private ImageButton editProfession,editCompany,editUniversity,saveProfession,saveCompany,saveUniversity;
    private EditText professionEditText,companyEditText,universityEditText;
    private ArrayList<String> sexualOrientation;

    private final String TAG="PROFILEACTIVITY";

    private String name,profession,company,university,email;
    private Date dob;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        moreText = findViewById(R.id.profileMore);
        nameText = findViewById(R.id.profileName);
        ageText = findViewById(R.id.profileAgeText);
        professionText = findViewById(R.id.profileProfession);
        companyText = findViewById(R.id.profileCompany);
        universityText = findViewById(R.id.profileUniversity);
        maleSwitch = findViewById(R.id.maleSwitch);
        femaleSwitch = findViewById(R.id.femaleSwitch);
        genderXSwitch = findViewById(R.id.theXSwitch);
        editProfession = findViewById(R.id.editProfession);
        editCompany = findViewById(R.id.editCompany);
        editUniversity = findViewById(R.id.editUniversity);
        saveProfession = findViewById(R.id.saveProfession);
        saveCompany = findViewById(R.id.saveCompany);
        saveUniversity = findViewById(R.id.saveUniversity);
        professionEditText = findViewById(R.id.profileProfessionEditText);
        companyEditText = findViewById(R.id.profileCompanyEditText);
        universityEditText = findViewById(R.id.profileUniversityEditText);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();

        Log.d(TAG,user.getEmail());

        ref = database.getReference("accounts");
         ref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                sexualOrientation = new ArrayList();

                String userID = snapshot.child("email").getValue(String.class);
                Log.d(TAG,"Email"+userID);
                name = snapshot.child("userName").getValue(String.class);
                profession = snapshot.child("profession").getValue(String.class);
                company = snapshot.child("company").getValue(String.class);
                university = snapshot.child("university").getValue(String.class);
                dob = snapshot.child("dob").getValue(Date.class);
                if(snapshot.child("sexualOrientation").getValue()!=null){
                    for(DataSnapshot o: snapshot.child("sexualOrientation").getChildren()){
                        sexualOrientation.add(o.getValue(String.class));
                    }
                }
                Log.d(TAG,"dob"+dob);
                Log.d(TAG,"name "+name);
//                if(name.equals("") || profession.equals("") || company.equals("") || university.equals("") || dob.equals(null) || sexualOrientation.equals(null)){
//                    Toast.makeText(ProfileActivity.this,"Error retrieving profile data \n Check your internet connection",Toast.LENGTH_SHORT).show();
//                } else{
                  nameText.setText(name);
                  professionText.setText(profession);
                  companyText.setText(company);
                  universityText.setText(university);
                  ageText.setText(String.valueOf(calculateAge(dob.getTime())));
                  for(Object o :sexualOrientation){
                      if(o.equals("Male")){
                          maleSwitch.setChecked(true);
                      }
                      if(o.equals("Female")){
                          femaleSwitch.setChecked(true);
                      }
                      if(o.equals("GenderX")){
                          genderXSwitch.setChecked(true);
                      }
                  }
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        moreText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MoreActivity.class));
            }
        });


        maleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !sexualOrientation.contains("Male")){
                    sexualOrientation.add("Male");
                }
                else if(!isChecked){
                    sexualOrientation.remove("Male");
                }
                ref.child(user.getUid()).child("sexualOrientation").setValue(sexualOrientation);
            }
        });

        femaleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !sexualOrientation.contains("Female")){
                    sexualOrientation.add("Female");
                }
                else if(!isChecked){
                    sexualOrientation.remove("Female");
                }

                ref.child(user.getUid()).child("sexualOrientation").setValue(sexualOrientation);
            }
        });

        genderXSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !sexualOrientation.contains("GenderX")){
                    sexualOrientation.add("GenderX");
                }
                else if(!isChecked){
                    sexualOrientation.remove("GenderX");
                }
                ref.child(user.getUid()).child("sexualOrientation").setValue(sexualOrientation);
            }
        });


        editProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                professionText.setVisibility(View.GONE);
                editProfession.setVisibility(View.GONE);
                saveProfession.setVisibility(View.VISIBLE);
                professionEditText.setVisibility(View.VISIBLE);
                professionEditText.setText(professionText.getText());
            }
        });

        saveProfession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profession = professionEditText.getText().toString();
                if(TextUtils.isEmpty(profession)){
                    Snackbar.make(v,"The field cannot be blank",Snackbar.LENGTH_SHORT).show();
                    profession = professionText.getText().toString();
                    return;
                }
                else{
                    ref.child(user.getUid()).child("profession").setValue(profession);
                }

                saveProfession.setVisibility(View.GONE);
                professionEditText.setVisibility(View.GONE);
                professionText.setVisibility(View.VISIBLE);
                editProfession.setVisibility(View.VISIBLE);
                professionText.setText(profession);
            }
        });


        editCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyText.setVisibility(View.GONE);
                editCompany.setVisibility(View.GONE);
                saveCompany.setVisibility(View.VISIBLE);
                companyEditText.setVisibility(View.VISIBLE);
                companyEditText.setText(companyText.getText());
            }
        });

        saveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                company = companyEditText.getText().toString();
                if(TextUtils.isEmpty(company)){
                    Snackbar.make(v,"The field cannot be blank",Snackbar.LENGTH_SHORT).show();
                    company = companyText.getText().toString();
                    return;
                }
                else{
                    ref.child(user.getUid()).child("company").setValue(company);
                }

                saveCompany.setVisibility(View.GONE);
                companyEditText.setVisibility(View.GONE);
                companyText.setVisibility(View.VISIBLE);
                editCompany.setVisibility(View.VISIBLE);
                companyText.setText(company);
            }
        });

        editUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universityText.setVisibility(View.GONE);
                editUniversity.setVisibility(View.GONE);
                saveUniversity.setVisibility(View.VISIBLE);
                universityEditText.setVisibility(View.VISIBLE);
                universityEditText.setText(universityText.getText());
            }
        });

        saveUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                university = universityEditText.getText().toString();
                if(TextUtils.isEmpty(university)){
                    Snackbar.make(v,"The field cannot be blank",Snackbar.LENGTH_SHORT).show();
                    university = universityText.getText().toString();
                    return;
                }
                else{
                    ref.child(user.getUid()).child("university").setValue(university);
                }

                saveUniversity.setVisibility(View.GONE);
                universityEditText.setVisibility(View.GONE);
                universityText.setVisibility(View.VISIBLE);
                editUniversity.setVisibility(View.VISIBLE);
                universityText.setText(university);
            }
        });



    }

    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;
    }
}
