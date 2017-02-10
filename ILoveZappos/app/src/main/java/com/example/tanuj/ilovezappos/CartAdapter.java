package com.example.tanuj.ilovezappos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanuj.ilovezappos.model.CartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanuj on 2/9/2017.
 */

public class CartAdapter extends ArrayAdapter<CartItem> {
Context context;
    int resource;
    ArrayList<CartItem> cartItems=new ArrayList<>();
    public CartAdapter(Context context, int resource, ArrayList<CartItem> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.cartItems=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cart_product, parent, false);
        TextView product_name = (TextView) rowView.findViewById(R.id.proName);
        TextView quantity = (TextView) rowView.findViewById(R.id.quantityC);
        TextView price = (TextView) rowView.findViewById(R.id.CPrice);
        TextView date = (TextView) rowView.findViewById(R.id.orderedDate);
        ImageView product_image = (ImageView) rowView.findViewById(R.id.proImg);
        product_name.setText(cartItems.get(position).getProductName());
        quantity.setText(String.valueOf(cartItems.get(position).getQuantity()));
        price.setText(cartItems.get(position).getDiscountedPrice());
        date.setText(cartItems.get(position).getItemAddedDate());
        Picasso.with(context).load(cartItems.get(position).getImageURL()).into(product_image);
        return rowView;
    }
}
