package com.hope.cs.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hope.cs.fooddeliveryapp.customerFoodPanel.CustomerHomeFragment;
import com.hope.cs.fooddeliveryapp.customerFoodPanel.CustomerOrdersFragment;
import com.hope.cs.fooddeliveryapp.customerFoodPanel.CustomerTrackerFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantHomeFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantOrderFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantPendingOrdersFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantProfileFragment;

public class RestaurantPanel_BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_panel_bottom_nav);
        BottomNavigationView navigationView = findViewById(R.id.res_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(name!=null){
            if(name.equalsIgnoreCase("OrderPage")){
                loadResFragment(new RestaurantPendingOrdersFragment());
            }else if(name.equalsIgnoreCase("ConfirmingOrderPage")){
                loadResFragment(new RestaurantOrderFragment());
            }else if(name.equalsIgnoreCase("AcceptingOrder")){
                loadResFragment(new RestaurantOrderFragment());
            }else if(name.equalsIgnoreCase("DeliveredPage")){
                loadResFragment(new RestaurantOrderFragment());
            }
        }else{
            loadResFragment(new RestaurantHomeFragment());
        }

         }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.restaurantHome:
                fragment=new RestaurantHomeFragment();
                break;
            case R.id.restaurantProfile:
                fragment=new RestaurantProfileFragment();
                break;
            case R.id.restaurantOrder:
                fragment=new RestaurantOrderFragment();
                break;
            case R.id.restaurantPendingOrder:
                fragment=new RestaurantPendingOrdersFragment();
                break;

        }
        return loadResFragment(fragment);
    }

    private boolean loadResFragment(Fragment fragment) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,fragment).commit();
            return true;
        }
        return false;
    }
}