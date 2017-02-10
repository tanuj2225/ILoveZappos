package com.example.tanuj.ilovezappos.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanuj on 2/7/2017.
 */

public class CartItem extends Product implements Parcelable {
    String cartId;
    String itemAddedDate;
    int quantity;

    public CartItem(){}
    public CartItem(Product product,String cartID,String cartDate,int quantity) {
        super(product.imageURL, product.productName, product.productURL, product.originalPrice, product.discountedPrice, product.percentOff, product.productID, product.brandName);
        this.cartId=cartID;
        this.itemAddedDate=cartDate;
        this.quantity=quantity;
    }

    protected CartItem(Parcel in) {
        cartId = in.readString();
        itemAddedDate = in.readString();
        productID=in.readString();
        productName=in.readString();
        brandName=in.readString();
        imageURL=in.readString();
        productURL=in.readString();
        discountedPrice=in.readString();
        originalPrice=in.readString();
        percentOff=in.readString();
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemAddedDate() {
        return itemAddedDate;
    }

    public void setItemAddedDate(String itemAddedDate) {
        this.itemAddedDate = itemAddedDate;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartId='" + cartId + '\'' +
                ", itemAddedDate=" + itemAddedDate + productID +
                '}';
    }
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("productID", this.getProductID());
        result.put("productName", this.getProductName());
        result.put("brandName", this.getBrandName());
        result.put("imageURL", this.getImageURL());
        result.put("productURL", this.getProductURL());
        result.put("discountedPrice", this.getDiscountedPrice());
        result.put("originalPrice", this.getOriginalPrice());
        result.put("percentOff", this.getPercentOff());
        result.put("cartId", this.getCartId());
        result.put("itemAddedDate", this.getItemAddedDate());
        result.put("quantity", this.getQuantity());
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cartId);
        parcel.writeString(itemAddedDate);
        parcel.writeString(productID);
        parcel.writeString(productName);
        parcel.writeString(brandName);
        parcel.writeString(imageURL);
        parcel.writeString(productURL);
        parcel.writeString(discountedPrice);
        parcel.writeString(originalPrice);
        parcel.writeString(percentOff);
        parcel.writeInt(quantity);
    }
}
