package com.example.deliveryfoodapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.example.deliveryfoodapp.Adapter.GridAdapter;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private GridView gridView;
    private TextView txtSearch;
    private GridAdapter gridAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gridView = findViewById(R.id.gridView);
        txtSearch = findViewById(R.id.txtSearch);

        Intent intent = getIntent();

        String searchText = intent.getStringExtra("searchText");
        String searchRightText = searchText.toLowerCase();

        txtSearch.setText(searchText.toUpperCase());

        ArrayList<FoodDomain> foodList = new ArrayList<>();

        switch (searchRightText){
            case "pizza":{
                foodList.add(new FoodDomain("Pizza Vegetable","pop_1", 9.0 ));
                foodList.add(new FoodDomain("Pizza Seafood","pop_3", 9.0 ));
                foodList.add(new FoodDomain("Pizza Sausage","pop_4", 9.0 ));
                foodList.add(new FoodDomain("Pizza Mix","pop_7", 10.0 ));
                foodList.add(new FoodDomain("Pizza Cheese","pop_8", 9.0 ));
                foodList.add(new FoodDomain("Pizza Ham","pop_9", 9.0 ));
                break;
            }
            case "burger":{
                foodList.add(new FoodDomain("Burger Chicken","pop_2", 8.0 ));
                foodList.add(new FoodDomain("Burger Cheese","pop_6", 8.0 ));
                break;
            }
            case "hot dog":{
                foodList.add(new FoodDomain("Hot dog","pop_5", 7.0 ));
                break;
            }
            case "potato":{
                foodList.add(new FoodDomain("Potato","pop_10", 5.0 ));
                break;
            }
            case "cake":{
                foodList.add(new FoodDomain("Strawberry Cake","bento1", 20.0 ));
                foodList.add(new FoodDomain("Mousse Cake","bento2", 15.0));
                foodList.add(new FoodDomain("Chocolate Cake","bento3", 18.0 ));
                foodList.add(new FoodDomain("Gato Cake","bento4", 19.0 ));
                foodList.add(new FoodDomain("Strawberry Cake","bento5", 17.0 ));
                foodList.add(new FoodDomain("Chocolate Cake","bento6", 17.0 ));
                foodList.add(new FoodDomain("Chocolate Cake","bento7", 17.0 ));
                foodList.add(new FoodDomain("Chocolate Cake","bento8", 7.0 ));
                foodList.add(new FoodDomain("Sweet Cake","cake5", 5.0 ));
                foodList.add(new FoodDomain("Sweet Cake","cake8", 4.0 ));
                foodList.add(new FoodDomain("Chocolate Sweet","cake1", 4.0 ));
                foodList.add(new FoodDomain("Chocolate Sweet","cake2", 5.0 ));
                break;
            }
            case "milk tea":{
                foodList.add(new FoodDomain("Milk tea","drink1", 3.0 ));
                foodList.add(new FoodDomain("Green milk tea","drink2", 4.0 ));
                break;
            }
            case "orange":{
                foodList.add(new FoodDomain("Orange","drink5", 5.0 ));
                break;
            }
            case "smoothies":{
                foodList.add(new FoodDomain("Smoothies","drink4", 6.0 ));
                break;
            }
            case "ice cream":{
                foodList.add(new FoodDomain("Ice Cream","drink6", 3.0 ));
                break;
            }
            case "coffee":{
                foodList.add(new FoodDomain("Coffee","drink3", 2.0 ));
                break;
            }
            case "waffle":{
                foodList.add(new FoodDomain("Waffle","cake6", 7.0 ));
                break;
            }
            case "bread":{
                foodList.add(new FoodDomain("Bread", "cake7", 2.0));
                break;
            }

        }

        gridAdapter = new GridAdapter(getBaseContext(),foodList);
        gridView.setAdapter(gridAdapter);
    }
}