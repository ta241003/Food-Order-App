package com.example.deliveryfoodapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryfoodapp.Domain.Payment;
import com.example.deliveryfoodapp.R;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    Context context;
    ArrayList<Payment> paymentArrayList;

    public PaymentAdapter(Context context, ArrayList<Payment> paymentArrayList) {
        this.context = context;
        this.paymentArrayList = paymentArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.viewholder_payment, parent, false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Payment payment = paymentArrayList.get(position);
        holder.txtGmail.setText(payment.getEmail());
        holder.txtTimeOrder.setText(payment.getTimeOrder());
        holder.txtAddressOrder.setText(payment.getAddress() + " ," + payment.getDistrict());
        holder.txtTotalItemOrder.setText(payment.getTotalItems());
        holder.txtDeliveryOrder.setText(payment.getDeliveryFee());
        holder.txtTaxFee.setText(payment.getTaxFee());
        holder.txtTotalBill.setText(payment.getTotalBill());
    }

    @Override
    public int getItemCount() {
        return paymentArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtGmail,txtTimeOrder, txtAddressOrder, txtTotalItemOrder, txtDeliveryOrder, txtTaxFee, txtTotalBill;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtGmail = itemView.findViewById(R.id.txtGmail);
            txtTimeOrder = itemView.findViewById(R.id.txtTimeOrder);
            txtAddressOrder = itemView.findViewById(R.id.txtAddressOrder);
            txtTotalItemOrder = itemView.findViewById(R.id.txtTotalItemOrder);
            txtDeliveryOrder = itemView.findViewById(R.id.txtDeliveryOrder);
            txtTaxFee = itemView.findViewById(R.id.txtTaxFee);
            txtTotalBill = itemView.findViewById(R.id.txtTotalBill);

        }
    }
}
