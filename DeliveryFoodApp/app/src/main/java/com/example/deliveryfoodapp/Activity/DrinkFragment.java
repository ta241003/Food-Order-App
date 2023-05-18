package com.example.deliveryfoodapp.Activity;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
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


public class DrinkFragment extends Fragment {

    private GridView gridView;
    private GridAdapter gridAdapter;

    public DrinkFragment(){
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drink, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
//       String[] title = {"Pizza Vegetable", "Burger Chicken", "Pizza Seafood", "Pizza Sausage", "Hot dog", "Burger Cheese", "Pizza Mix", "Pizza Cheese", "Pizza Ham", "Potato"};
//        String[] fee = {"4", "3", "5", "4", "3", "3", "6", "5", "5", "2"};
//        int[] image = {R.drawable.pop_1, R.drawable.pop_2, R.drawable.pop_3, R.drawable.pop_4, R.drawable.pop_5, R.drawable.pop_6, R.drawable.pop_7, R.drawable.pop_8, R.drawable.pop_9, R.drawable.pop_10};
        foodList.add(new FoodDomain("Milk tea","drink1", 3.0 ));
        foodList.add(new FoodDomain("Green milk tea","drink2", 4.0 ));
        foodList.add(new FoodDomain("Coffee","drink3", 2.0 ));
        foodList.add(new FoodDomain("Smoothies","drink4", 6.0 ));
        foodList.add(new FoodDomain("Orange","drink5", 5.0 ));
        foodList.add(new FoodDomain("Lemon","drink6", 3.0 ));

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