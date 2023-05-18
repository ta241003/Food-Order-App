package com.example.deliveryfoodapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.deliveryfoodapp.Adapter.ReceiptAdapter;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.Domain.Order;
import com.example.deliveryfoodapp.Domain.User;
import com.example.deliveryfoodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class CheckOutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public int i = 1;
    public static EditText edtUsername, edtEmail, edtPhone, edtAddress, edtMessage;
    TextView txtTotalItem, txtDeliveryFee, txtTaxFee, txtTotalOrder;
    Button btnPlaceOrder;
    Spinner mySpinner;
    ProgressBar progressBarChechout;

//    public static String fullname, email, phone, message, address;
//    public static String totalItem, deliveryFee, taxFee, totalFee, districtAddress;

    public static String text, districtAddress ;


    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference, referenceOder;
    private String userID;


    ArrayAdapter<String> adapterDistricts;

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewReceiptList;
    private ArrayList<FoodDomain> receiptFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        txtTotalItem = findViewById(R.id.txtTotalItem);
        txtDeliveryFee = findViewById(R.id.txtDeliveryFee);
        txtTaxFee = findViewById(R.id.txtTaxFee);
        txtTotalOrder = findViewById(R.id.txtTotalOrder);

        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        edtMessage = findViewById(R.id.edtMessage);

        progressBarChechout = findViewById(R.id.progressBarChechout);

        mySpinner = findViewById(R.id.spinnerDistrict);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.districts));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(this);

        showUserData();
        showOrderData();
        showReceipt();
        placeOrder();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        districtAddress = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Your District: "+districtAddress, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showReceipt() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        Intent intent = getIntent();
        receiptFood = (ArrayList<FoodDomain>) intent.getSerializableExtra("object");

        recyclerViewReceiptList = findViewById(R.id.rvReceipt);
        recyclerViewReceiptList.setLayoutManager(linearLayoutManager);
        adapter = new ReceiptAdapter(receiptFood);
        recyclerViewReceiptList.setAdapter(adapter);
    }

    public void showUserData() {

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String  fullname = userProfile.fullname;
                    String email = userProfile.email;
                    String phone = userProfile.phone;

                    edtUsername.setText(fullname);
                    edtEmail.setText(email);
                    edtPhone.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckOutActivity.this, "Something wrong happend!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showOrderData() {

        Intent intent = getIntent();

        String totalItem = intent.getStringExtra("totalItem");
        String deliveryFee = intent.getStringExtra("deliveryFee");
        String taxFee = intent.getStringExtra("taxFee");
        String totalFee = intent.getStringExtra("totalFee");


        txtTotalItem.setText(totalItem);
        txtDeliveryFee.setText(deliveryFee);
        txtTaxFee.setText(taxFee);
        txtTotalOrder.setText(totalFee);

    }

    private void placeOrder() {

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String fullname = edtUsername.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String addressss = edtAddress.getText().toString();
                String totalItem = txtTotalItem.getText().toString().trim();
                String deliveryFee = txtDeliveryFee.getText().toString().trim();
                String taxFee = txtTaxFee.getText().toString().trim();
                String totalOrder = txtTotalOrder.getText().toString().trim();
                String message = edtMessage.getText().toString().trim();

                if (addressss.trim().isEmpty()) {
                    edtAddress.setError("Please enter information");
                    edtAddress.requestFocus();
                    return;
                }

                database = FirebaseDatabase.getInstance();
                referenceOder = database.getReference("Order");

//                Calendar calendar = Calendar.getInstance();
//                String timeOrder = DateFormat.getDateInstance().format(calendar.getTime());

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String timeOrder = dateFormat.format(calendar.getTime());
                DatabaseReference orderRef = referenceOder.push();
                String orderId = orderRef.getKey(); // Get the unique key generated by Firebase for the new order



                if (edtMessage.getText().toString().isEmpty()) {
                    Order order = new Order(fullname, email, phone, addressss, districtAddress, totalItem, deliveryFee, taxFee, totalOrder, timeOrder);

                    orderRef.setValue(order); // Save the new order to Firebase
                } else {
                    Order order = new Order(fullname, email, phone, addressss, districtAddress, message, totalItem, deliveryFee, taxFee, totalOrder, timeOrder);
                    orderRef.setValue(order); // Save the new order to Firebase
                }

                Toast.makeText(CheckOutActivity.this, "You place order Successfully !", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CheckOutActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

}