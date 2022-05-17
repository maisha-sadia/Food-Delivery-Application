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
import com.hope.cs.fooddeliveryapp.customerFoodPanel.CustomerProfileFragment;
import com.hope.cs.fooddeliveryapp.customerFoodPanel.CustomerShoppingCartFragment;
import com.hope.cs.fooddeliveryapp.customerFoodPanel.CustomerTrackerFragment;
import com.hope.cs.fooddeliveryapp.deliveryFoodPanel.DeliveryPendingOrdersFragment;
import com.hope.cs.fooddeliveryapp.deliveryFoodPanel.DeliveryTakeOrdersFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantHomeFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantOrderFragment;
import com.hope.cs.fooddeliveryapp.resFoodPanel.RestaurantPendingOrdersFragment;

public class DeliveryGuyPanel_BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_guy_panel_bottom_nav);
        BottomNavigationView navigationView = findViewById(R.id.cus_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(name!=null){
            if(name.equalsIgnoreCase("DeliveryOrderPage")){
                loadDeliveryFragment(new DeliveryPendingOrdersFragment());
            }
        }else{
            loadDeliveryFragment(new DeliveryPendingOrdersFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.delivery_pending_orders:
                fragment=new DeliveryPendingOrdersFragment();
                break;
            case R.id.delivery_shipping_orders:
                fragment=new DeliveryTakeOrdersFragment();
                break;
        }
        return loadDeliveryFragment(fragment);
    }

    private boolean loadDeliveryFragment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,fragment).commit();
            return true;
        }
        return false;
    }
}