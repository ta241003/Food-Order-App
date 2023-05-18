package com.example.deliveryfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.deliveryfoodapp.Adapter.ViewPagerAdapter;
import com.example.deliveryfoodapp.R;
import com.google.android.material.tabs.TabLayout;

public class ShopActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        tabLayout = findViewById(R.id.tablayout_shop);
        viewPager = findViewById(R.id.viewpager_shop);
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new PizzaFragment(), "PIZZA");
        viewPagerAdapter.addFragment(new BentoFragment(), "BENTO");
        viewPagerAdapter.addFragment(new CakeFragment(), "CAKE");
        viewPagerAdapter.addFragment(new DrinkFragment(), "DRINK");
        viewPager.setAdapter(viewPagerAdapter);
    }


}