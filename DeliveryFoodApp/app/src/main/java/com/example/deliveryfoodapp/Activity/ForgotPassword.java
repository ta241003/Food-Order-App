package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deliveryfoodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText edt_InputEmail;
    private Button btnResetPassword;
    private ProgressBar progressBarr;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_InputEmail = findViewById(R.id.edt_inputemail);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        progressBarr = findViewById(R.id.progressBarrr);

        auth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = edt_InputEmail.getText().toString().trim();

        if (email.isEmpty()) {
            edt_InputEmail.setError("Email is required!");
            edt_InputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_InputEmail.setError("Please enter a valid email!");
            edt_InputEmail.requestFocus();
            return;
        }

        progressBarr.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgotPassword.this, "Try again! Something wrong happend!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}