package com.example.deliveryfoodapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliveryfoodapp.Activity.PizzaFragment;
import com.example.deliveryfoodapp.Activity.ShowDetailActivity;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    Context context;
    ArrayList<FoodDomain> popularFood;

    LayoutInflater inflater;

    public GridAdapter(Context context,ArrayList<FoodDomain> popularFood) {
        this.context = context;
        this.popularFood = popularFood;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.viewholder_tab_layout, null);

        ImageView imageView = convertView.findViewById(R.id.pic);
        TextView text_title = convertView.findViewById(R.id.title);
        TextView text_fee = convertView.findViewById(R.id.fee);
        TextView addBtn = convertView.findViewById(R.id.addBtn);

        int picture = convertView.getContext().getResources().getIdentifier(popularFood.get(position).getPic(), "drawable", convertView.getContext().getPackageName());


        Glide.with(convertView)
                .load(picture)
                .into(imageView);

        text_title.setText(popularFood.get(position).getTitle());
        text_fee.setText(String.valueOf(popularFood.get(position).getFee()));

        View finalConvertView = convertView;
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("object", popularFood.get(position));
                finalConvertView.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public int getCount() {
        return popularFood.size();
    }
}
