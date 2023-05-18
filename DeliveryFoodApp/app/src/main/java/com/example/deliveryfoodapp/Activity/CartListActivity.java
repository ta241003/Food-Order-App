package com.example.deliveryfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.deliveryfoodapp.Adapter.CartListAdapter;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.Helper.ManagementCart;
import com.example.deliveryfoodapp.Interface.ChangeNumberItemsListener;
import com.example.deliveryfoodapp.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private ArrayList<FoodDomain> foodDomains;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    Button btnCheckOut;
    private double tax;
    private ScrollView scrollView;
    private int s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        initView();
        initList();
        CalculateCart();
        bottomNavigation();



        btnCheckOut = findViewById(R.id.btnCheckOut);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passUserData();
            }
        });
    }


    private void  bottomNavigation() {
        FloatingActionButton floatingActionButton  = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout supportBtn = findViewById(R.id.supportBtn);
        LinearLayout shoptBtn = findViewById(R.id.shopBtn);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, HomeActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, ProfileActivity.class));
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, SupportActivity.class));
            }
        });

        shoptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, ShopActivity.class));
            }
        });

    }


    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerView);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.cartView);


    }
    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        foodDomains = managementCart.getListCart();
        adapter = new CartListAdapter(managementCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                CalculateCart();
                foodDomains = managementCart.getListCart();
                if (managementCart.getListCart().isEmpty()) {
                    s = 0;
                    setNotificationCart();
                    emptyTxt.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                }
                else {
                    s = managementCart.getListCart().size();
                    setNotificationCart();
                    emptyTxt.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerViewList.setAdapter(adapter);
        if (managementCart.getListCart().isEmpty()) {
            s = 0;
            setNotificationCart();
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
        else {
            s = managementCart.getListCart().size();
            setNotificationCart();
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }


    private void  CalculateCart() {
        double percentTax = 0.02;
        double delevery = 10;

        tax = Math.round((managementCart.getTotalFee() * percentTax) * 100) / 100;
        double total = Math.round((managementCart.getTotalFee() + tax + delevery) * 100) / 100;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100;

        totalTxt.setText("S" + itemTotal);
        taxTxt.setText("$" + tax);
        deliveryTxt.setText("$" + delevery);
        totalFeeTxt.setText("$" + total);
    }

    private void passUserData() {
        String totalItem = totalTxt.getText().toString();
        String deliveryFee = deliveryTxt.getText().toString();
        String taxFee = taxTxt.getText().toString();
        String totalFee = totalFeeTxt.getText().toString();


        Intent intent = new Intent(CartListActivity.this, CheckOutActivity.class);

        intent.putExtra("totalItem", totalItem);
        intent.putExtra("deliveryFee", deliveryFee);
        intent.putExtra("taxFee", taxFee);
        intent.putExtra("totalFee", totalFee);
        intent.putExtra("object", foodDomains);

        startActivity(intent);
    }

    public void setNotificationCart() {
        FloatingActionButton floatingAction_btn = findViewById(R.id.cartBtn);
        BadgeDrawable badgeDrawable = BadgeDrawable.create(CartListActivity.this);
        floatingAction_btn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                badgeDrawable.setNumber(s);
                badgeDrawable.setHorizontalOffset(30);
                badgeDrawable.setVerticalOffset(20);
                badgeDrawable.setBackgroundColor(ContextCompat.getColor(CartListActivity.this, R.color.red));

                BadgeUtils.attachBadgeDrawable(badgeDrawable, floatingAction_btn, null);
            }
        });
    }
}