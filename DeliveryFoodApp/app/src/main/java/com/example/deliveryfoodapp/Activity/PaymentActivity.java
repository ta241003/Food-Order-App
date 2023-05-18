package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.deliveryfoodapp.Adapter.PaymentAdapter;
import com.example.deliveryfoodapp.Domain.Payment;
import com.example.deliveryfoodapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    PaymentAdapter paymentAdapter;
    ArrayList<Payment> paymentArrayList;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        recyclerView = findViewById(R.id.listPayment);
        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        firebaseAuth = FirebaseAuth.getInstance();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        paymentArrayList = new ArrayList<>();
        paymentAdapter = new PaymentAdapter(this, paymentArrayList);
        recyclerView.setAdapter(paymentAdapter);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Payment payment = dataSnapshot.getValue(Payment.class);
                    // Check if the order is placed by the current user

                    if (payment.getEmail().equals(firebaseAuth.getCurrentUser().getEmail())) {
                        paymentArrayList.add(payment);
                    }
                }
                paymentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}