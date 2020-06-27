package com.fx.folx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    private EditText otp;
    private Button otpContinue;
    private TextView resend;

    //It is the verification id that will be sent to the user
    private String mVerificationId;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    //firebase auth object
    private FirebaseAuth mAuth;

    private final String TAG = "OTPACTIVITY";
    private String receivedCredential;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();

        final User u = (User) getIntent().getSerializableExtra("New User");
        String phoneNumber = u.getPhone();

        otpContinue = findViewById(R.id.otpContinue);
        otp = findViewById(R.id.otpText);
        resend = findViewById(R.id.resend);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                Toast.makeText(OTPActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Toast.makeText(OTPActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(OTPActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
                mVerificationId = verificationId; //Add this line to save //verification Id
                mResendToken = token; //Add this line to save the resend token
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,      // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks



        otpContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(otp.getText().toString())) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp.getText().toString());
                    mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(OTPActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(OTPActivity.this, "Verification Success", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(OTPActivity.this,GenderActivity.class);
                                        i.putExtra("New User", u);
                                        startActivity(i);
                                    } else {
                                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            Toast.makeText(OTPActivity.this, "Verification Failed, Invalid credentials", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
                else{
                    otp.setError("Invalid Code");
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        u.getPhone(),        // Phone number to verify
                        30  ,               // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        OTPActivity.this,               // Activity (for callback binding)
                        mCallbacks,         // OnVerificationStateChangedCallbacks
                        mResendToken);             // Force Resending Token from callbacks
            }
        });
    }


}
