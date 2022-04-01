package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private EditText email, password;
    TextView forgotpassword;
    Button login;
    private String userid;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        login = findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        forgotpassword = findViewById(R.id.frgtPass);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(view.getContext(), forgotPassword.class));
            }
        });
    }

    public void Signup(View view) {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Login.this);

        builder.setMessage("How do you want to Register Yourself?");
        builder.setTitle("Rent-Labs");
        builder.setCancelable(false);
        builder.setNegativeButton("As User", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent userIntent = new Intent(view.getContext(), userRegistration.class);
                startActivity(userIntent);

            }
        });
        builder.setPositiveButton("As Provider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent providerIntent = new Intent(view.getContext(), providerRegistration.class);
                startActivity(providerIntent);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void userLogin(View view) {

        String lEmail = email.getText().toString().trim();
        String lPassword = password.getText().toString().trim();


        if (lEmail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(lEmail).matches()) {
            email.setError(("Please Enter a valid email!"));
            email.requestFocus();
            return;
        }
        if (lPassword.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if (lPassword.length() < 6) {
            password.setError("Minnimum Password Length is 6 characters");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(lEmail, lPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userid = mAuth.getCurrentUser().getUid();

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String type = dataSnapshot.child("UserType").getValue(String.class);
                                Toast.makeText(Login.this, "Logged in Succefully.", Toast.LENGTH_SHORT).show();
                                String aEmail = dataSnapshot.child("Email").getValue(String.class);
                                String aPassword = dataSnapshot.child("Password").getValue(String.class);


                                if (aEmail.equals("upendrabeth@gmail.com") && aPassword.equals("rentlabs")){
                                    Toast.makeText(Login.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, Admin.class);
                                    startActivity(intent);
                                    finish();
                                } else if (type.equals("User")) {
                                    Intent intent = new Intent(Login.this, userHomepage.class);
                                    startActivity(intent);
                                    finish();
                                } else if (type.equals("Provider")) {
                                    Intent intent = new Intent(Login.this, providerHome.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Failed to Login!! Please check you Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onBackPressed() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Login.this);

        builder.setMessage("Do you want to Exit?");
        builder.setTitle("Rent-Labs");
        builder.setCancelable(false);
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(1);
            }
        });
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}