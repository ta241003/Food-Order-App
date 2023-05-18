package com.example.deliveryfoodapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.deliveryfoodapp.Domain.FoodDomain;
import com.example.deliveryfoodapp.Interface.ChangeNumberItemsListener;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listFood.size(); i++) {
            if (listFood.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            listFood.get(n).setNumberInCart(item.getNumberInCart());
            Toast.makeText(context, "Produce is existed in your cart!", Toast.LENGTH_LONG).show();
        } else {
            listFood.add(item);
            Toast.makeText(context, "Add to cart successfully!", Toast.LENGTH_LONG).show();
        }
        tinyDB.putListObject("CartList", listFood);
    }

    public ArrayList<FoodDomain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listFood.get(position).setNumberInCart(listFood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listFood);
        changeNumberItemsListener.change();
    }

    public void minusNumberFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.change();
    }

    public void deleteFood(ArrayList<FoodDomain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listfood.remove(position);
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.change();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listFood = getListCart();
        double fee = 0;
        for (int i = 0; i < listFood.size(); i++) {
            fee = fee + (listFood.get(i).getFee() * listFood.get(i).getNumberInCart());
        }
        return fee;
    }
}