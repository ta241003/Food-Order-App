package com.example.deliveryfoodapp.Activity;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.deliveryfoodapp.Adapter.GridAdapter;
import com.example.deliveryfoodapp.Adapter.PopularAdapter;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.R;

import java.util.ArrayList;


public class PizzaFragment extends Fragment {

    private GridView gridView;

    private GridAdapter gridAdapter;

    public PizzaFragment(){
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pizza, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Pizza Vegetable","pop_1", 9.0 ));
        foodList.add(new FoodDomain("Burger Chicken","pop_2", 8.0 ));
        foodList.add(new FoodDomain("Pizza Seafood","pop_3", 9.0 ));
        foodList.add(new FoodDomain("Pizza Sausage","pop_4", 9.0 ));
        foodList.add(new FoodDomain("Hot dog","pop_5", 7.0 ));
        foodList.add(new FoodDomain("Burger Cheese","pop_6", 8.0 ));
        foodList.add(new FoodDomain("Pizza Mix","pop_7", 10.0 ));
        foodList.add(new FoodDomain("Pizza Cheese","pop_8", 9.0 ));
        foodList.add(new FoodDomain("Pizza Ham","pop_9", 9.0 ));
        foodList.add(new FoodDomain("Potato","pop_10", 5.0 ));

        gridAdapter = new GridAdapter(getActivity(),foodList);
        gridView.setAdapter(gridAdapter);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("");
    }

}
