package com.example.tanuj.ilovezappos;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanuj.ilovezappos.databinding.ActivityZapposProductDetailBinding;
import com.example.tanuj.ilovezappos.model.CartItem;
import com.example.tanuj.ilovezappos.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class zappos_product_detail extends AppCompatActivity implements View.OnClickListener {
    Product product;
    CartItem item;
    CartItem cartItem;
    ImageView productImg,popupImg;
    ShareActionProvider mShareActionProvider;
    private int not_number = 0;
    private TextView not_text = null;
    MenuItem notifcation_badge;
    public static String productImgUrl = "";
    PopupWindow popUpWindow;
    private PopupWindow pw;
    TextView prodName,brandName;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    Button chooseQuantity;
    AlertDialog.Builder quantityDialog;
    FloatingActionButton fab;
    int quantity=1;
    String value="";
    String[] items;
    int pos;
    ArrayList<CartItem> cartList;
    ArrayList<CartItem> gotCartList;
    CartAdapter cartAdapter;
    ListView cartListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zappos_product_detail);
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dbRef=database.getReference();
        cartList=new ArrayList<CartItem>();
        gotCartList=new ArrayList<CartItem>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setAllowEnterTransitionOverlap(false);
        }
        if(getIntent().getExtras().getSerializable(HomeScreen.PRODUCT)!=null){
            product= (Product) getIntent().getExtras().getSerializable(HomeScreen.PRODUCT);

        }
        if(getIntent().getExtras().getSerializable(HomeScreen.CARTLIST)!=null){
            gotCartList=getIntent().getExtras().getParcelableArrayList(HomeScreen.CARTLIST);

        }
        ActivityZapposProductDetailBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_zappos_product_detail);
        binding.setProduct(product);
        productImg= (ImageView) findViewById(R.id.prodImage);
        Picasso.with(this).load(product.getImageURL()).into(productImg);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_page_menu, menu);
        MenuItem share=menu.findItem(R.id.share);
        MenuItem close=menu.findItem(R.id.back);
        notifcation_badge= menu.findItem(R.id.myCart);
        not_text= (TextView) notifcation_badge.getActionView().findViewById(R.id.messageIcon);
        not_text.setText(String.valueOf(gotCartList.size()));
        MenuItemCompat.getActionView(notifcation_badge).setOnClickListener(new View.OnClickListener() {
                                                                               @Override
                                                                               public void onClick(View view) {
                                                                                   CartActions.generateCartPopUp(gotCartList, cartListView,cartAdapter,zappos_product_detail.this);
                                                                               }
                                                                           });
                mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(share);
        setShareIntent(createShareIntent());
        close.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                finish();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                product.getProductURL());
        return shareIntent;
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    public boolean addToCart(){
        String key = dbRef.child(auth.getCurrentUser().getUid()).child("CartItems").push().getKey();
        Date date=new Date();
        SimpleDateFormat dt = new SimpleDateFormat("MM-dd-yyyy");
        item=new CartItem(product,key,dt.format(date).toString(),Integer.parseInt(items[pos]));
        Map<String, Object> postValues = item.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/CartItems/" +auth.getCurrentUser().getUid()+ "/" + key, postValues);
        dbRef.updateChildren(childUpdates);
        getCartList();
        return true;
    }
    public void updateCartCount(final int new_hot_number) {
        Animation fadeIn = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.fade_in);
        final Animation blink = new AnimationUtils().loadAnimation(getApplicationContext(), R.anim.blink);
        fab.setAnimation(fadeIn);
        fab.startAnimation(fadeIn);
        not_text.setAnimation(blink);
        if (not_text == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    not_text.setVisibility(View.INVISIBLE);
                else {
                    not_text.setVisibility(View.VISIBLE);
                    not_text.setText(String.valueOf(new_hot_number));
                    not_text.startAnimation(blink);
                }
            }
        });
    }
    public  void getCartList(){
        Query query=dbRef.child("CartItems").child(auth.getCurrentUser().getUid());
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                cartItem=dataSnapshot.getValue(CartItem.class);
                cartList.add(cartItem);
                updateCartCount(cartList.size());
                gotCartList=new ArrayList<CartItem>();
                gotCartList.addAll(cartList);
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
    private void initiatePopupWindow(Product product) {
        try {

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            items=getResources().getStringArray(R.array.quantities);
            final AlertDialog alertDialog;
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Quantity");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    pos=i;
                }
            });
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(addToCart()) {
                        fab.setImageResource(R.drawable.done);
                        fab.setBackgroundTintList(ColorStateList.valueOf(Color
                                .parseColor("#AED581")));
                        fab.setClickable(false);
                    }
                }
            });
            builder.setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alertDialog = builder.create();
            lp.copyFrom(alertDialog.getWindow().getAttributes());
            lp.height = 1000;
            alertDialog.show();
            alertDialog.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.fab:
                initiatePopupWindow(product);
                break;
        }
    }
}
