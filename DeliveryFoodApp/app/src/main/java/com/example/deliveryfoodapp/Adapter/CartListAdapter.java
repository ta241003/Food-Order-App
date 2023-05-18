package com.example.deliveryfoodapp.Adapter;


import static android.system.Os.remove;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.system.ErrnoException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliveryfoodapp.Activity.CartListActivity;
import com.example.deliveryfoodapp.Activity.ShowDetailActivity;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.Helper.ManagementCart;
import com.example.deliveryfoodapp.Interface.ChangeNumberItemsListener;
import com.example.deliveryfoodapp.R;

import java.io.IOException;
import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    Context context;
    private ArrayList<FoodDomain> foodDomains;

    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;


    public CartListAdapter(ArrayList<FoodDomain> foodDomains, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.context = context;
        this.foodDomains = foodDomains;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);

        return new ViewHolder(inflate);
    }


    /// **************************************************************************
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(foodDomains.get(position).getTitle());
//        Log.d("title", holder.title.getText().toString());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getNumberInCart() * foodDomains.get(position).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);


        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.plusNumberFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    remove(String.valueOf(holder.getAdapterPosition()));
                } catch (ErrnoException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        });

        holder.txt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managementCart.deleteFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void change() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.change();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num, txt_delete;
        Button btnCheckOut;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            txt_delete = itemView.findViewById(R.id.txt_delete);
        }
    }
}
