package com.example.tanuj.ilovezappos.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.tanuj.ilovezappos.BR;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;
import java.io.Serializable;

/**
 * Created by tanuj on 1/28/2017.
 */



public class Product extends BaseObservable implements Serializable {
    @SerializedName("productName")
    String productName;
    @SerializedName("thumbnailImageUrl")
    String imageURL;
    @SerializedName("productUrl")
    String productURL;
    @SerializedName("originalPrice")
    String originalPrice;
    @SerializedName("price")
    String discountedPrice;
    @SerializedName("percentOff")
    String percentOff;
    @SerializedName("productId")
    String productID;
    @SerializedName("brandName")
    String brandName;


    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyPropertyChanged(BR.productName);
    }

    @Bindable
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        notifyPropertyChanged(BR.imageURL);
    }

    @Bindable
    public String getProductURL() {
        return productURL;
    }

    public void setProductURL(String productURL) {
        this.productURL = productURL;
        notifyPropertyChanged(BR.productURL);
    }

    @Bindable
    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
        notifyPropertyChanged(BR.originalPrice);
    }

    @Bindable
    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
        notifyPropertyChanged(BR.discountedPrice);
    }

    @Bindable
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
        notifyPropertyChanged(BR.productID);
    }

    @Bindable
    public String getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(String percentOff) {
        this.percentOff = percentOff;
        notifyPropertyChanged(BR.percentOff);
    }

    @Bindable
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
        notifyPropertyChanged(BR.brandName);
    }
public Product(){}
    public Product(String imageURL, String productName, String productURL, String originalPrice, String discountedPrice, String percentOff, String productID, String brandName) {
        this.imageURL = imageURL;
        this.productName = productName;
        this.productURL = productURL;
        this.originalPrice = originalPrice;
        this.discountedPrice = discountedPrice;
        this.percentOff = percentOff;
        this.productID = productID;
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", productURL='" + productURL + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", discountedPrice='" + discountedPrice + '\'' +
                ", percentOff='" + percentOff + '\'' +
                ", productID='" + productID + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
