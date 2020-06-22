package com.fx.folx.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fx.folx.MainActivity;
import com.fx.folx.R;
import com.fx.folx.SignIn;

/**
 *Sign in Fragment
 */
public class SignInFragment extends Fragment {

    private EditText signInEmail, signInPassword;
    private Button signIn;
    private TextView forgotPass;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_in, container, false);

        signInEmail = root.findViewById(R.id.signInEmail);
        signInPassword = root.findViewById(R.id.signInPassword);

        signIn = root.findViewById(R.id.signInButton);
        forgotPass = root.findViewById(R.id.forgotPassword);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });



        return root;
    }
}