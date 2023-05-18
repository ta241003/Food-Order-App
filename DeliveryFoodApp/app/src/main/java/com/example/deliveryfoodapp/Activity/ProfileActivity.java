package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.deliveryfoodapp.R;
import com.example.deliveryfoodapp.Domain.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

     LinearLayout btnLogout, btnPayment, btnMembership, linear_layout_password;
     Button btnEditProfile,btnBack;
     TextView greetingTextView, fullNameTextView, emailTextView, passwordTextView, phoneTextView;

     View view2;

    ImageView imgAvatarUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogout = findViewById(R.id.btnLogout);
        btnMembership = findViewById(R.id.btnMembership);
        btnPayment = findViewById(R.id.btnPayment);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnBack = findViewById(R.id.btnBack);

        linear_layout_password = findViewById(R.id.linear_layout_password);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        greetingTextView = findViewById(R.id.txt_greeting);
        fullNameTextView = findViewById(R.id.txt_fullName);
        emailTextView = findViewById(R.id.txt_emailAddress);
        passwordTextView = findViewById(R.id.txt_password);
        phoneTextView = findViewById(R.id.txt_phone);
        imgAvatarUser = findViewById(R.id.imgAvatarUser);

        view2 = findViewById(R.id.view2);

        showUserData();
        checkAccount();


        btnMembership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MembershipActivity.class);
                startActivity(intent);
            }
        });


        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //signOutGoogle();
                signOut();
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    });

    }

    private void checkAccount() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.getAccessToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {
                String strProvider = getTokenResult.getSignInProvider();
                if (strProvider.equals("google.com")) {
                    view2.setVisibility(View.GONE);
                    linear_layout_password.setVisibility(View.GONE);
                }
            }
        });
    }

    private void signOut() {
        DialogPlus dialogPlus = DialogPlus.newDialog(ProfileActivity.this)
            .setContentBackgroundResource(R.color.transparent)
            .setContentHolder(new ViewHolder(R.layout.dialog_exit))
            .setGravity(Gravity.CENTER)
            .setCancelable(true)
            .create();

        View view = dialogPlus.getHolderView();

        Button cancel_button = view.findViewById(R.id.dialog_exit_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPlus.dismiss();
            }
        });

        Button exit_button = view.findViewById(R.id.dialog_exit_exit_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
        dialogPlus.show();

    }


    public void showUserData() {

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.fullname;
                    String email = userProfile.email;
                    String password = userProfile.password;
                    String phone = userProfile.phone;
                    String imageUrl = userProfile.avatar;

                    greetingTextView.setText("Hi, " + fullname + "!");
                    fullNameTextView.setText(fullname);
                    emailTextView.setText(email);
                    passwordTextView.setText("*******");
                    phoneTextView.setText(phone);

                    Glide.with(ProfileActivity.this)
                            .load(imageUrl)
                            .transform(new CircleCrop())
                            .into(imgAvatarUser);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Something wrong happend!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void passUserData() {
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.fullname;
                    String email = userProfile.email;
                    String password = userProfile.password;
                    String phone = userProfile.phone;
                    String avatar = userProfile.avatar;

                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);

                    intent.putExtra("name", fullname);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("phone", phone);
                    intent.putExtra("avatar", avatar);

                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    }
