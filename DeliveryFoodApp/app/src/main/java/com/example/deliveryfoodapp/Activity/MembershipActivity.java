package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliveryfoodapp.Domain.Order;
import com.example.deliveryfoodapp.Domain.User;
import com.example.deliveryfoodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class MembershipActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference, reference2;


    private String userID;

    private ImageView btn_back;
    private TextView txt_username1, txt_username2, txt_ranking1, txt_ranking2, txt_sum;

    private static String fullname, currentEmail;

    float total = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        user = FirebaseAuth.getInstance().getCurrentUser();
        currentEmail = user.getEmail();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference2 = FirebaseDatabase.getInstance().getReference("Order");
        userID = user.getUid();

        btn_back = findViewById(R.id.btn_back);
        txt_username1 = findViewById(R.id.txt_username1);
        txt_username2 = findViewById(R.id.txt_username2);
        txt_ranking1 = findViewById(R.id.txt_ranking1);
        txt_ranking2 = findViewById(R.id.txt_ranking2);
        txt_sum = findViewById(R.id.txt_sum);




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MembershipActivity.this, ProfileActivity.class));
            }
        });

        showUserData();
        showSum();
        checkMember();
    }

    private void checkMember() {
        if (total >= 0.0f && total < 100.0f) {
            txt_ranking1.setText("You are currently a Bronze member!");
            txt_ranking2.setText("Congratulations , you have reached Bronze in the 2023 cycle !");
        } else if (total >= 100.0f && total < 200.0f) {
            txt_ranking1.setText("You are currently a Silver member!");
            txt_ranking2.setText("Congratulations , you have reached Silver in the 2023 cycle !");
        } else if (total >= 200.0f && total < 500.0f) {
            txt_ranking1.setText("You are currently a Gold member!");
            txt_ranking2.setText("Congratulations , you have reached Gold in the 2023 cycle !");
        } else if (total >= 500.0f && total < 1000.0f) {
            txt_ranking1.setText("You are currently a Platinum member!");
            txt_ranking2.setText("Congratulations , you have reached Platinum in the 2023 cycle !");
        } else if (total >= 1000.0f) {
            txt_ranking1.setText("You are currently a Diamond member!");
            txt_ranking2.setText("Congratulations , you have reached Diamond in the 2023 cycle !");
        }
    }

    public void showUserData() {

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    fullname = userProfile.fullname;

                    txt_username1.setText("Best regards, " + fullname);
                    txt_username2.setText(fullname);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MembershipActivity.this, "Something wrong happend!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSum() {

        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int count = 0;

                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    String username = orderSnapshot.child("email").getValue(String.class);
                    if (username.equals(currentEmail)) {
                        String totalItemString = orderSnapshot.child("totalBill").getValue(String.class);
                        float totalItem = Float.parseFloat(totalItemString.replace("$", "").trim());
                        total += totalItem;
                        count++;
                    }
                }
//                txt_sum.setText(String.valueOf(total));
                txt_sum.setText("Thank you for spending " +  total +"$" +  " and buying " + count +" orders!");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi
            }
        });
    }
    
    
}