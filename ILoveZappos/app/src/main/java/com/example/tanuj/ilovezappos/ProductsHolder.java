package com.example.tanuj.ilovezappos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tanuj on 1/28/2017.
 */

public class ProductsHolder extends RecyclerView.ViewHolder {
    TextView productName,brandName,price;
    ImageView productImage;
    LinearLayout layout;
    public ProductsHolder(View itemView) {
        super(itemView);
        productName= (TextView) itemView.findViewById(R.id.productName);
        brandName= (TextView) itemView.findViewById(R.id.brandName);
        productImage= (ImageView) itemView.findViewById(R.id.productImg);
        price= (TextView) itemView.findViewById(R.id.price);
        layout= (LinearLayout) itemView.findViewById(R.id.itemLayout);
    }
}
