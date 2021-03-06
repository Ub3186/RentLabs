package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    FirebaseAuth auth;
    private EditText resetemail;
    private Button resetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        resetemail = findViewById(R.id.forgotemail);
        resetpass = findViewById(R.id.reset);

        auth = FirebaseAuth.getInstance();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = resetemail.getText().toString();

        if (email.isEmpty()) {
            resetemail.setError("Email is required!");
            resetemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            resetemail.setError("Please provide Valid email!");
            resetemail.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(forgotPassword.this, "Check Your Email to reset your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(forgotPassword.this, "Something Went Wrong! Please Try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}