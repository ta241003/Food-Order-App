package com.example.deliveryfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.deliveryfoodapp.Domain.MessageModel;
import com.example.deliveryfoodapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout shopBtn = findViewById(R.id.shopBtn);
        FirebaseDatabase db;
        DatabaseReference ref;

//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button btn_send_support = findViewById(R.id.btn_send_support);
        EditText edt_mailname = findViewById(R.id.edt_mailname);
        EditText edt_message = findViewById(R.id.edt_message);

        db = FirebaseDatabase.getInstance();
        ref = db.getReference().child("Message");


        btn_send_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = edt_mailname.getText().toString();
                String message = edt_message.getText().toString();
                if (mail.isEmpty()) {
                    edt_mailname.setError("Email is required");
                    edt_mailname.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    edt_mailname.setError("Please provide valid email");
                    edt_mailname.requestFocus();
                    return;
                }

                if (message.isEmpty()) {
                    edt_message.setError("Message is required");
                    edt_message.requestFocus();
                    return;
                }

                MessageModel messageModel = new MessageModel(mail, message);

                ref.push().setValue(messageModel);
                edt_mailname.setText("");
                edt_message.setText("");
            }
        });

        bottomNavigation();
    }

    private void  bottomNavigation() {
        FloatingActionButton floatingActionButton  = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout supportBtn = findViewById(R.id.supportBtn);
        LinearLayout shopBtn = findViewById(R.id.shopBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, HomeActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, ProfileActivity.class));
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, SupportActivity.class));
            }
        });

        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, ShopActivity.class));
            }
        });

    }
}