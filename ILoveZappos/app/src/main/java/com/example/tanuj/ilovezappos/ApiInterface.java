package com.example.tanuj.ilovezappos;

import com.example.tanuj.ilovezappos.model.ProductList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tanuj on 1/28/2017.
 */

public interface ApiInterface {
    @GET("Search")
    Call<ProductList> getProductsByKeyword(@Query("term") String term,@Query("key") String apiKey);
}
