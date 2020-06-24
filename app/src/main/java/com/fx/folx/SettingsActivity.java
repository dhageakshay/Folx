package com.fx.folx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.Date;

public class SettingsActivity extends AppCompatActivity {

    private TextView distanceTextView, ageTextView;
    private SeekBar distanceSeekbar;
    private RangeSeekBar ageseek;
    private int minAge, maxAge;
    private RadioGroup lastSeen;

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

        distanceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                distanceTextView.setText((progress + 20) +" kms");
                distance = progress+20;
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

        ageseek.setSelectedMaxValue(25);
        ageseek.setSelectedMinValue(18);

        ageseek.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
               minAge = (int) bar.getSelectedMinValue();
               maxAge = (int)bar.getSelectedMaxValue();

               ageTextView.setText(minAge+"-"+maxAge+" years");
            }
        });


        lastSeen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);

            }
        });


    }
}
