package com.example.deliveryfoodapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.R;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    ArrayList<FoodDomain> receiptFood;

    public ReceiptAdapter(ArrayList<FoodDomain> receiptFood) {
        this.receiptFood = receiptFood;
    }

    @Override
    public ReceiptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_receipt, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView6.setText(receiptFood.get(position).getTitle());
        holder.textView9.setText(String.valueOf(Math.round((receiptFood.get(position).getNumberInCart() * receiptFood.get(position).getFee()) * 100) / 100));
        holder.textView7.setText(String.valueOf(receiptFood.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(receiptFood.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.imageView4);

    }

    @Override
    public int getItemCount() {
        return receiptFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView6, textView7, textView9;
        ImageView imageView4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView6 = itemView.findViewById(R.id.textView6);
            textView7 = itemView.findViewById(R.id.textView7);
            textView9 = itemView.findViewById(R.id.textView9);
            imageView4 = itemView.findViewById(R.id.imageView4);
        }
    }
}