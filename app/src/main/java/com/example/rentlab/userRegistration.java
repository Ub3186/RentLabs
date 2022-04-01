package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class userRegistration extends AppCompatActivity {

    private EditText userFirstName, userLastName, userEmail, userPassword, confirmUserPassword, userAadhar;
    private Button uSubmitbtn;
    private String uUserType = "User";
    private String userid, uFirstname, uLastname, uEmail, uPassword, uConfirmPassword, uAadhar;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        userFirstName = findViewById(R.id.userFirstname);
        userLastName = findViewById(R.id.userLastname);
        userEmail = findViewById(R.id.userEmailid);
        userPassword = findViewById(R.id.usrPassword);
        confirmUserPassword = findViewById(R.id.confirmUsrPassword);
        userAadhar = findViewById(R.id.userAadharNumber);


        uSubmitbtn = findViewById(R.id.userRegisterbtn);

        uSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrive the strings
                uFirstname = userFirstName.getText().toString();
                uLastname = userLastName.getText().toString();
                uEmail = userEmail.getText().toString();
                uPassword = userPassword.getText().toString();
                uConfirmPassword = confirmUserPassword.getText().toString();
                uAadhar = userAadhar.getText().toString();


                // check for empty input
                if (uFirstname.isEmpty()) {
                    userFirstName.setError("First name is required!");
                    userFirstName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uLastname)) {
                    userLastName.setError("Last name is required!");
                    userLastName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uEmail)) {
                    userEmail.setError("Email is required!");
                    userEmail.requestFocus();
                    return;
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(uEmail).matches())) {
                    userEmail.setError("Please provide a Valid email id!");
                    userEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uPassword)) {
                    userPassword.setError("Please Enter Password!");
                    userPassword.requestFocus();
                    return;
                }
                if (uPassword.length() < 6) {
                    userPassword.setError("Password should have minimum 6 characters!");
                    userPassword.requestFocus();
                    return;
                }
                if (!(uConfirmPassword.equals(uPassword))) {
                    confirmUserPassword.setError("Confirm Password and Password Does not match");
                    confirmUserPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(uAadhar)) {
                    userAadhar.setError("Aadhar is required!");
                    userAadhar.requestFocus();
                    return;
                }
                if (uAadhar.length() < 12) {
                    userAadhar.setError("Please Enter a Valid Aadhar number");
                    userAadhar.requestFocus();
                    return;
                }

                //Save data to firebase
                fAuth = FirebaseAuth.getInstance();

                uSubmitbtn.setText("Registering...");
                fAuth.createUserWithEmailAndPassword(uEmail, uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userid = Objects.requireNonNull(fAuth.getCurrentUser().getUid());

                            userHelperClass uHelperClass = new userHelperClass(userid, uFirstname, uLastname, uEmail, uPassword, uAadhar, uUserType);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(uHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        uSubmitbtn.setText("Register");
                                        Toast.makeText(userRegistration.this, "Registration Successful Welcome to Rentlabs", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent userIntent = new Intent(view.getContext(), Login.class);
                                        startActivity(userIntent);
                                    } else {
                                        uSubmitbtn.setText("Register");
                                        Toast.makeText(userRegistration.this, "Registration failed try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(userRegistration.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}