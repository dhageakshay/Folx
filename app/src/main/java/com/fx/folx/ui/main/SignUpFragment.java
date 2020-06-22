package com.fx.folx.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fx.folx.OTPActivity;
import com.fx.folx.R;

/**
 * Sign up fragment
 */
public class SignUpFragment extends Fragment {

    private EditText signUpName, signUpEmail, signUpPassword, signUpConfirm, signUpDOB, signUpPhone;
    private Button continueButton;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signUpEmail = root.findViewById(R.id.signUpEmail);
        signUpName = root.findViewById(R.id.signUpName);
        signUpPassword = root.findViewById(R.id.signUpPassword);
        signUpConfirm = root.findViewById(R.id.confirmPassword);
        signUpDOB = root.findViewById(R.id.signUpdob);
        signUpPhone = root.findViewById(R.id.signUpPhone);

        continueButton = root.findViewById(R.id.signUpContinue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OTPActivity.class));
            }
        });


        return root;
    }
}