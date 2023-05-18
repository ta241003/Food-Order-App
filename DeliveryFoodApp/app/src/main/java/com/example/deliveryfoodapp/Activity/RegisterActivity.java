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
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryfoodapp.R;
import com.example.deliveryfoodapp.Domain.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, txt_pageLogin;
    private Button btnRegister;
    private EditText edt_FullName, edt_Phone, edt_Email,edt_InputPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        edt_FullName = findViewById(R.id.edt_FullName);
        edt_Phone = findViewById(R.id.edt_Phone);
        edt_Email = findViewById(R.id.edt_Email);
        edt_InputPassword = findViewById(R.id.edt_InputPassword);

        txt_pageLogin = findViewById(R.id.txt_PageLogin);
        txt_pageLogin.setOnClickListener(this);

        progressBar = findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnRegister:
                registerUser();
                break;
            case R.id.txt_PageLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    private void registerUser() {
        String fullname = edt_FullName.getText().toString().trim();
        String phone = edt_Phone.getText().toString().trim();
        String email = edt_Email.getText().toString().trim();
        String password = edt_InputPassword.getText().toString().trim();

        if (fullname.isEmpty()) {
            edt_FullName.setError("Fullname is required");
            edt_FullName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            edt_Phone.setError("Phone is required");
            edt_Phone.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edt_Email.setError("Email is required");
            edt_Email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_Email.setError("Please provide valid email");
            edt_Email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edt_InputPassword.setError("Password is required");
            edt_InputPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edt_InputPassword.setError("Min password length should be 6 characters!");
            edt_InputPassword.requestFocus();
            return;
        }

        if (phone.length() > 10 ) {
            edt_Phone.setError("Max phone length should be 10 numbers!");
            edt_Phone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user= new User(fullname, phone, email, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registered Succesfully!", Toast.LENGTH_LONG).show();
                                                FirebaseAuth.getInstance().signOut();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish(); // close the RegisterActivity
                                            }else {
                                                Toast.makeText(RegisterActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });
                        } else  {
                            Toast.makeText(RegisterActivity.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}