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

public class providerRegistration extends AppCompatActivity {

    private EditText providerFirstName, providerLastName, providerEmail, providerPassword, providerConfirmPassword, providerAadhar, pshopName, pshopID, pshopAddress, pshopEmail, pshopContact;
    private Button pSubmitbtn;
    private String pUserType = "Provider";
    private String userid, pFirstname, pLastname, pEmail, pPassword, pConfirmPassword, pAadhar, sName, sID, sAddress, sEmail, sContact;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_registration);

        providerFirstName = findViewById(R.id.pFirstname);
        providerLastName = findViewById(R.id.pLastname);
        providerEmail = findViewById(R.id.pEmail);
        providerPassword = findViewById(R.id.pPassword);
        providerConfirmPassword = findViewById(R.id.pConfirmPassword);
        providerAadhar = findViewById(R.id.pAadhar);
        pshopName = findViewById(R.id.shopName);
        pshopID = findViewById(R.id.shopID);
        pshopAddress = findViewById(R.id.shopAddress);
        pshopEmail = findViewById(R.id.shopEmail);
        pshopContact = findViewById(R.id.shopContact);

        pSubmitbtn = findViewById(R.id.providerRegisterBtn);

        pSubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pFirstname = providerFirstName.getText().toString();
                pLastname = providerLastName.getText().toString();
                pEmail = providerEmail.getText().toString();
                pPassword = providerPassword.getText().toString();
                pConfirmPassword = providerConfirmPassword.getText().toString();
                pAadhar = providerAadhar.getText().toString();
                sName = pshopName.getText().toString();
                sID = pshopID.getText().toString();
                sAddress = pshopAddress.getText().toString();
                sEmail = pshopEmail.getText().toString();
                sContact = pshopContact.getText().toString();

                // check for empty input
                if (TextUtils.isEmpty(pFirstname)) {
                    providerFirstName.setError("First name is required!");
                    providerFirstName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pLastname)) {
                    providerLastName.setError("Last name is required!");
                    providerLastName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pEmail)) {
                    providerEmail.setError("Email is required!");
                    providerEmail.requestFocus();
                    return;
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(pEmail).matches())) {
                    providerEmail.setError("Please provide a Valid email id!");
                    providerEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pPassword)) {
                    providerPassword.setError("Please Enter Password!");
                    providerPassword.requestFocus();
                    return;
                }
                if (pPassword.length() < 6) {
                    providerPassword.setError("Password should have minimum 6 characters!");
                    providerPassword.requestFocus();
                    return;
                }
                if (!(pConfirmPassword.equals(pPassword))) {
                    providerConfirmPassword.setError("Confirm Password and Password Does not match");
                    providerConfirmPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pAadhar)) {
                    providerAadhar.setError("Aadhar is required!");
                    providerAadhar.requestFocus();
                    return;
                }
                if (pAadhar.length() < 12) {
                    providerAadhar.setError("Please Enter a Valid Aadhar number");
                    providerAadhar.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(sName)) {
                    pshopName.setError("Shop Name required!");
                    pshopName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(sID)) {
                    pshopID.setError("Shop ID required!");
                    pshopID.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(sAddress)) {
                    pshopAddress.setError("Shop address is required!");
                    pshopAddress.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(sEmail)) {
                    pshopEmail.setError("Shop Email is required!");
                    pshopEmail.requestFocus();
                    return;
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())) {
                    pshopEmail.setError("Please provide a Valid email id!");
                    pshopEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(sContact)) {
                    pshopContact.setError("Contact number is required!");
                    pshopContact.requestFocus();
                    return;
                }
                if (sContact.length() < 10) {
                    pshopContact.setError("Given Contact is not valid!");
                    pshopContact.requestFocus();
                    return;
                }

                fAuth = FirebaseAuth.getInstance();

                pSubmitbtn.setText("Registering...");
                fAuth.createUserWithEmailAndPassword(pEmail, pPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userid = Objects.requireNonNull(fAuth.getCurrentUser().getUid());

                            providerHelperClass pHelperClass = new providerHelperClass(userid, pFirstname, pLastname, pEmail, pPassword, pAadhar, pUserType, sName, sID, sAddress, sEmail, sContact);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(pHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pSubmitbtn.setText("Register");
                                        Toast.makeText(providerRegistration.this, "Registration Successful! Welcome to Rentlabs", Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent userIntent = new Intent(view.getContext(), Login.class);
                                        startActivity(userIntent);
                                    } else {
                                        pSubmitbtn.setText("Register");
                                        Toast.makeText(providerRegistration.this, "Registration failed try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });


    }
}