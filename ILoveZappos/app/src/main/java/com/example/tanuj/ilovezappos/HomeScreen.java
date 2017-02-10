package com.example.tanuj.ilovezappos;

import android.app.ActivityOptions;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanuj.ilovezappos.model.CartItem;
import com.example.tanuj.ilovezappos.model.Product;
import com.example.tanuj.ilovezappos.model.ProductList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

public class HomeScreen extends AppCompatActivity implements ProductsAdapter.LinkData {

    LinearLayout searchresults,welcomepanel;
    RecyclerView products_view;
    ProductsAdapter productsAdapter;
    public final static String API_KEY = "b743e26728e16b81da139182bb2094357c31d331";
    public final static String CARTLIST = "cartlist";
    public ProductList productData;
    ArrayList<Product> products;
    public static final String PRODUCT="productObj";
    private int not_number = 0;
    private TextView not_text = null;
    MenuItem notifcation_badge;
    TextView Search_Keyword,count;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    CartItem cartItem;
    ArrayList<CartItem> cartList=new ArrayList<>();
    CartAdapter cartAdapter;
    ListView cartListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        searchresults= (LinearLayout) findViewById(R.id.searchResults);
        welcomepanel= (LinearLayout) findViewById(R.id.welcomePanel);
        products_view= (RecyclerView) findViewById(R.id.productResults);
        auth = FirebaseAuth.getInstance();
        Search_Keyword= (TextView) findViewById(R.id.Search_Keyword);
        count= (TextView) findViewById(R.id.count);
        database = FirebaseDatabase.getInstance();
        dbRef=database.getReference();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.zappos_menu, menu);
        notifcation_badge= menu.findItem(R.id.myCart);
        MenuItem label= menu.findItem(R.id.Label);
        not_text= (TextView) notifcation_badge.getActionView().findViewById(R.id.messageIcon);
        not_text.setVisibility(View.INVISIBLE);
        label.setTitle(auth.getCurrentUser().getEmail());
        MenuItemCompat.getActionView(notifcation_badge).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartActions.generateCartPopUp(cartList, cartListView,cartAdapter,HomeScreen.this);
            }
        });
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getProductsFromSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout:
                auth.signOut();
                Intent intent=new Intent(HomeScreen.this,WelcomeScreen.class);
                startActivity(intent);
                break;
    }
        return true;
    }
    public void getProductsFromSearch(String keyword){
        welcomepanel.setVisibility(View.INVISIBLE);
        searchresults.setVisibility(View.VISIBLE);
        Search_Keyword.setText(keyword);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ProductList> getProducts = apiService.getProductsByKeyword(keyword,API_KEY);
        getProducts.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                productData=response.body();
                products = response.body().getProductList();
                productsAdapter=new ProductsAdapter(HomeScreen.this,products);
                count.setText(products.size()+" Results");
                LinearLayoutManager verticalLayoutManager
                        = new LinearLayoutManager(HomeScreen.this, LinearLayoutManager.VERTICAL, false);
                products_view.setLayoutManager(verticalLayoutManager);
                products_view.setAdapter(productsAdapter);
                productsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {

            }
        });
    }
    public  void getCart(){

        Query query=dbRef.child("CartItems").child(auth.getCurrentUser().getUid());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                cartItem=dataSnapshot.getValue(CartItem.class);
                cartList.add(cartItem);
                not_text.setVisibility(View.VISIBLE);
                not_text.setText(String.valueOf(cartList.size()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartList.clear();
        getCart();
    }

    @Override
    public void getProductData(Product product) {
        Intent intent =new Intent(HomeScreen.this,zappos_product_detail.class);
        intent.putExtra(PRODUCT,product);
        if(cartList!=null && cartList.size()>0){
            intent.putParcelableArrayListExtra(CARTLIST,cartList);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(this);
            startActivity(intent, options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }
}
