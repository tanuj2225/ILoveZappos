package com.example.tanuj.ilovezappos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tanuj.ilovezappos.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tanuj on 1/28/2017.
 */

public class ProductsAdapter extends RecyclerView.Adapter{
    HomeScreen activity;
    ArrayList<Product> productList;

    public ProductsAdapter(HomeScreen activity, ArrayList<Product> productList) {
        this.activity = activity;
        this.productList = productList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        ProductsHolder productsHolder=new ProductsHolder(v);
        return productsHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Product product=productList.get(position);
        final ProductsHolder holderObj= (ProductsHolder) holder;
        holderObj.productName.setText(product.getProductName());
        holderObj.brandName.setText(product.getBrandName());
        holderObj.price.setText(product.getDiscountedPrice());
        holderObj.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getProductData(product);

            }
        });
        Picasso.with(activity).load(product.getImageURL()).into(holderObj.productImage);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public  interface LinkData{
        void getProductData(Product product);
    }
}
