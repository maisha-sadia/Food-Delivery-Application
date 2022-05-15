package com.hope.cs.fooddeliveryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }
}