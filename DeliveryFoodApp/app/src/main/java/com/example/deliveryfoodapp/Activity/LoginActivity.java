package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliveryfoodapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 1234;
    private TextView txt_pageRegister, txt_ForgotPassword;
    private EditText edt_InputEmail, edt_InputPassword;
    private Button btnLogin;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ImageView btnGoogle;

    private GoogleSignInClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_pageRegister = findViewById(R.id.txt_pageRegister);
        txt_pageRegister.setOnClickListener(this);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        edt_InputEmail = findViewById(R.id.edt_InputEmail);
        edt_InputPassword = findViewById(R.id.edt_InputPassword);

        progressBar = findViewById(R.id.progressBarr);

        mAuth = FirebaseAuth.getInstance();

        txt_ForgotPassword = findViewById(R.id.txt_ForgotPassword);
        txt_ForgotPassword.setOnClickListener(this);

        btnGoogle = findViewById(R.id.btnGoogle);
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this, options);

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i, RC_SIGN_IN);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_pageRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.btnLogin:
                userlogin();
                break;

            case R.id.txt_ForgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // Save user's data to Firebase Realtime Database
                                    saveUserDataToDatabase(user);
                                    // Navigate to Home screen
                                    navigateToHome();
                                } else {
                                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
    private void saveUserDataToDatabase(FirebaseUser user) {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        userRef.child("email").setValue(user.getEmail());
        userRef.child("fullname").setValue(user.getDisplayName());
//        userRef.child("phone").setValue(user.getPhoneNumber());
        userRef.child("avatar").setValue(user.getPhotoUrl().toString());
    }

    // Navigate to Home screen
    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void userlogin() {
        String email = edt_InputEmail.getText().toString().trim();
        String password = edt_InputPassword.getText().toString().trim();

        if (email.isEmpty()) {
            edt_InputEmail.setError("Email is required");
            edt_InputEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_InputEmail.setError("Please enter a valid email!");
            edt_InputEmail.requestFocus();
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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            if (user.isEmailVerified()) {
                                checkAndUpdatePasswordInRealtimeDatabase(password);
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(LoginActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            edt_InputPassword.setText("");
                            Toast.makeText(LoginActivity.this, "Failed to login! Try Again!", Toast.LENGTH_LONG).show();
                        }

                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void checkAndUpdatePasswordInRealtimeDatabase(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        String userId = user.getUid();

        DatabaseReference passwordRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("password");
        passwordRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String currentPassword = dataSnapshot.getValue(String.class);

                if (!currentPassword.equals(password)) {
                    passwordRef.setValue(password)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }



}