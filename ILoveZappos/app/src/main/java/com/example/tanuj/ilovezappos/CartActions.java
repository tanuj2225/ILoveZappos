package com.example.tanuj.ilovezappos;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanuj.ilovezappos.model.CartItem;

import java.util.ArrayList;

/**
 * Created by tanuj on 2/9/2017.
 */

public class CartActions {
    public static void generateCartPopUp(ArrayList<CartItem> cartList, ListView cartListView, CartAdapter cartAdapter, Context context){

           Dialog dialog = new Dialog(context);
           dialog.setContentView(R.layout.cart_pop_up);
           dialog.setTitle("Cart Items");
            cartListView = (ListView) dialog.findViewById(R.id.cartListView);
        TextView noItems= (TextView) dialog.findViewById(R.id.cartPopupLabel);
        if(cartList.size()>0) {

            cartListView.setVisibility(View.VISIBLE);
            noItems.setVisibility(View.INVISIBLE);
            cartAdapter = new CartAdapter(context, R.layout.cart_product, cartList);
            cartListView.setAdapter(cartAdapter);
        }
        else if(cartList.size()==0){
            cartListView.setVisibility(View.INVISIBLE);
            noItems.setVisibility(View.VISIBLE);
        }
        dialog.show();
       }

    }

