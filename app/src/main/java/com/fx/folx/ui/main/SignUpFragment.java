package com.fx.folx.ui.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fx.folx.OTPActivity;
import com.fx.folx.R;
import com.fx.folx.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sign up fragment
 */
public class SignUpFragment extends Fragment {

    private final String TAG="SIGNUPFRAGMENT";

    private EditText signUpName, signUpEmail, signUpPassword, signUpConfirm, signUpDOB, signUpPhone, signUpNickName;
    private Button continueButton;
    private String email,password,name,confirmPass,nickName,dob,phone;
    private Boolean isAllFilled = true;

    Calendar myCalendar;
    private FirebaseAuth mAuth;
    private User newUser;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);

        myCalendar = Calendar.getInstance();

        signUpEmail = root.findViewById(R.id.signUpEmail);
        signUpName = root.findViewById(R.id.signUpName);
        signUpPassword = root.findViewById(R.id.signUpPassword);
        signUpConfirm = root.findViewById(R.id.confirmPassword);
        signUpDOB = root.findViewById(R.id.signUpdob);
        signUpPhone = root.findViewById(R.id.signUpPhone);
        signUpNickName = root.findViewById(R.id.signUpNickName);

        continueButton = root.findViewById(R.id.signUpContinue);



        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        signUpDOB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFilled = true;

                email = signUpEmail.getText().toString();
                password = signUpPassword.getText().toString();
                name = signUpName.getText().toString();
                confirmPass = signUpConfirm.getText().toString();
                dob = signUpDOB.getText().toString();
                nickName = signUpNickName.getText().toString();
                phone = signUpPhone.getText().toString();


//                Toast.makeText(getActivity(),String.valueOf(calculateAge(myCalendar.getTimeInMillis())),Toast.LENGTH_LONG).show();



                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError("Can't leave this field empty!");
                    isAllFilled = false;

                }
                if(TextUtils.isEmpty(password)){
                    signUpPassword.setError("Can't leave this field empty!");
                    isAllFilled = false;

                }
                if(TextUtils.isEmpty(confirmPass)){
                    signUpConfirm.setError("Can't leave this field empty!");
                    isAllFilled = false;

                }
                if(TextUtils.isEmpty(name)){
                    signUpName.setError("Can't leave this field empty!");
                    isAllFilled = false;

                }
                if(TextUtils.isEmpty(dob)){
                    signUpDOB.setError("Can't leave this field empty!");
                    isAllFilled = false;

                }
                if(TextUtils.isEmpty(phone)){
                    signUpPhone.setError("Can't leave this field empty!");
                    isAllFilled = false;

                }


                if(!isValidMail(email)){
                    signUpEmail.setError("Invalid Email");
                    isAllFilled = false;

                }

                if(!isValidMobile(phone)){
                    signUpPhone.setError("Invalid Phone");
                    isAllFilled = false;
                }

                if(calculateAge(myCalendar.getTimeInMillis())<18){
                    signUpDOB.setError("You must be 18 to continue");
                    isAllFilled = false;
                }

                if(!confirmPass.equals(password)){
                    signUpConfirm.setError("Passwords do not match");
                    isAllFilled = false;
                }

                if(password.length()<8){
                    signUpPassword.setError("Password length should be greater than 8");
                    isAllFilled = false;
                }
                if(!isValidPassword(password)){
                    signUpPassword.setError("Password should contain at least one uppercase one lowercase and one symbol and one of these symbol @#$%^&+=!");
                    isAllFilled = false;
                }


                if(isAllFilled) {
                    newUser = null;
                    try {
                        newUser = new User(name, email, new SimpleDateFormat("MM/dd/yyyy").parse(dob),phone);
                        newUser.setName(name);
                        Log.d(TAG,newUser.getName());
                        newUser.setMaxAgeRange(25);
                        newUser.setMinAgeRange(18);
                        newUser.setMaxDistance(100);
                        if(nickName!=null){
                            newUser.setNickName(nickName);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Enter DOB in the right format", Toast.LENGTH_SHORT).show();
                    }


                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getActivity(),"Authentication Successful",Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getActivity(), OTPActivity.class);
                                        i.putExtra("New User",  newUser);
                                        startActivity(i);
                                    }
                                    else{
                                        Toast.makeText(getActivity(),"E-mail or password is wrong",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }

            }
        });
        return root;

    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        signUpDOB.setText(sdf.format(myCalendar.getTime()));
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

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


}