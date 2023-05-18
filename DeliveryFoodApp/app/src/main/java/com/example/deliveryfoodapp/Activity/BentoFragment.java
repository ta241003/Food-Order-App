package com.example.deliveryfoodapp.Activity;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

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


public class BentoFragment extends Fragment {

    private GridView gridView;
    private GridAdapter gridAdapter;

    public BentoFragment(){
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bento, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Strawberry Cake","bento1", 20.0 ));
        foodList.add(new FoodDomain("Mousse Cake","bento2", 15.0 ));
        foodList.add(new FoodDomain("Chocolate Cake","bento3", 18.0 ));
        foodList.add(new FoodDomain("Gato Cake","bento4", 19.0 ));
        foodList.add(new FoodDomain("Strawberry Cake","bento5", 17.0 ));
        foodList.add(new FoodDomain("Chocolate Cake","bento6", 17.0 ));
        foodList.add(new FoodDomain("Tiramisu Torte","bento7", 19.0 ));
        foodList.add(new FoodDomain("Carot Cake","bento8", 7.0 ));

        gridAdapter = new GridAdapter(getActivity(),foodList);
        gridView.setAdapter(gridAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Bento");
    }

}