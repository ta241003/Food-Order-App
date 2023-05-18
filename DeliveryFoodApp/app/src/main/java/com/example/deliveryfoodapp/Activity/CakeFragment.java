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


public class CakeFragment extends Fragment {

    private GridView gridView;
    private GridAdapter gridAdapter;

    public CakeFragment(){
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cake, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);

        ArrayList<FoodDomain> foodList = new ArrayList<>();
        foodList.add(new FoodDomain("Chocolate Sweet","cake1", 4.0 ));
        foodList.add(new FoodDomain("Mix Sweet","cake2", 5.0 ));
        foodList.add(new FoodDomain("Donut","cake3", 3.0 ));
        foodList.add(new FoodDomain("Mix Donut","cake4", 5.0 ));
        foodList.add(new FoodDomain("Sweet Cake","cake5", 5.0 ));
        foodList.add(new FoodDomain("Waffle","cake6",7.0  ));
        foodList.add(new FoodDomain("Bread","cake7", 2.0 ));
        foodList.add(new FoodDomain("Sweet Cake","cake8", 4.0 ));

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
