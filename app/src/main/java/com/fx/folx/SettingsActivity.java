package com.fx.folx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private final String TAG = "SETTINGSACTIVITY";

    private TextView distanceTextView, ageTextView;
    private SeekBar distanceSeekbar;
    private RangeSeekBar ageseek;
    private int minAge, maxAge;
    private RadioGroup lastSeen;
    private Button logout;

    private Integer minAgeRange,maxAgeRange,maxDistance;
    private Boolean viewLastSeen;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    DatabaseReference ref;

    int distance=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        distanceTextView = findViewById(R.id.distanceTextView);
        ageTextView = findViewById(R.id.ageTextView);
        distanceSeekbar = findViewById(R.id.distanceSeekbar);
        ageseek = findViewById(R.id.ageRange);
        lastSeen = findViewById(R.id.lastSeen);
        logout = findViewById(R.id.logoutButton);

        mAuth = FirebaseAuth.getInstance();
        user  = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("accounts");
        ref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                minAgeRange = snapshot.child("minAgeRange").getValue(Integer.class);
                maxAgeRange = snapshot.child("maxAgeRange").getValue(Integer.class);
                maxDistance = snapshot.child("maxDistance").getValue(Integer.class);
                viewLastSeen = (Boolean) snapshot.child("viewLastSeen").getValue(Boolean.class);
                Log.d(TAG,String.valueOf(viewLastSeen));
                Log.d(TAG,String.valueOf(maxDistance));

                if(viewLastSeen){
                    lastSeen.check(R.id.publicRadio);
                }else{
                    lastSeen.check(R.id.privateRadio);
                }

                ageseek.setSelectedMinValue(minAgeRange);
                ageseek.setSelectedMaxValue(maxAgeRange);
                distanceSeekbar.setProgress(maxDistance-20);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        distanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                distanceTextView.setText((progress + 20) +" kms");
                distance = progress+20;
                ref.child(user.getUid()).child("maxDistance").setValue(distance);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
              //  Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
             //   Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });


        ageseek.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
               minAge = (int) bar.getSelectedMinValue();
               maxAge = (int)bar.getSelectedMaxValue();

               ageTextView.setText(minAge+"-"+maxAge+" years");

               ref.child(user.getUid()).child("minAgeRange").setValue(minAge);
                ref.child(user.getUid()).child("maxAgeRange").setValue(maxAge);
            }
        });


        lastSeen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if(checkedId == R.id.privateRadio){
                    ref.child(user.getUid()).child("viewLastSeen").setValue(false);
                }
                else{
                    ref.child(user.getUid()).child("viewLastSeen").setValue(true);

                }

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),OnBoardingActivity.class));
                finish();
            }
        });

    }
}
