package com.example.tanuj.ilovezappos.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tanuj on 1/28/2017.
 */

public class ProductList {
    @SerializedName("originalTerm")
    String productSearchKey;
    @SerializedName("currentResultCount")
    int count;
    @SerializedName("totalResultCount")
    int totalCount;
    @SerializedName("results")
    ArrayList<Product> productList;
    @SerializedName("statusCode")
    int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "ProductList{" +
                "productSearchKey='" + productSearchKey + '\'' +
                ", count=" + count +
                ", totalCount=" + totalCount +
                ", productList=" + productList +
                '}';
    }

    public ProductList(String productSearchKey, int count, int totalCount, ArrayList<Product> productList, int statusCode) {
        this.productSearchKey = productSearchKey;
        this.count = count;
        this.totalCount = totalCount;
        this.productList = productList;
        this.statusCode = statusCode;
    }

    public String getProductSearchKey() {
        return productSearchKey;
    }

    public void setProductSearchKey(String productSearchKey) {
        this.productSearchKey = productSearchKey;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
}
