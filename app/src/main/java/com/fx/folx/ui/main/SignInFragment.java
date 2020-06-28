package com.fx.folx.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fx.folx.ForgotPasswordActivity;
import com.fx.folx.R;
import com.fx.folx.SwipeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *Sign in Fragment
 */
public class SignInFragment extends Fragment {

    private EditText signInEmail, signInPassword;
    private Button signIn;
    private TextView forgotPass;
    private FirebaseAuth mAuth;
    String email,password;
    final String TAG = "SIGNINFRAGMENT";

    private Boolean isAllFilled;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signInEmail = root.findViewById(R.id.signInEmail);
        signInPassword = root.findViewById(R.id.signInPassword);

        signIn = root.findViewById(R.id.signInButton);
        forgotPass = root.findViewById(R.id.forgotPassword);

        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAllFilled = true;

                email = signInEmail.getText().toString();
                password = signInPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    signInEmail.setError("Enter email");
                    isAllFilled = false;
                }
                if(TextUtils.isEmpty(password)){
                    isAllFilled = false;
                    signInPassword.setError("Enter Password");
                }

                if(isAllFilled){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent i = new Intent(getActivity(), SwipeActivity.class);
                                        i.putExtra("Sign In",user);
                                        startActivity(i);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        // ...
                                    }

                                    // ...
                                }
                            });
                }

                forgotPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
                    }
                });

            }
        });





        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }
}