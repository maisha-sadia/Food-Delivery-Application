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

public class CustomerPanel_BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_panel_bottom_nav);
        BottomNavigationView navigationView = findViewById(R.id.cus_bottom_nav);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(name!=null){
            if(name.equalsIgnoreCase("Homepage")){
                loadCustomerFragment(new CustomerHomeFragment());
            }else if(name.equalsIgnoreCase("PreparingPage")){
                loadCustomerFragment(new CustomerTrackerFragment());
            }else if(name.equalsIgnoreCase("DeliveryPage")){
                loadCustomerFragment(new CustomerTrackerFragment());
            }else if(name.equalsIgnoreCase("ThankYouPage")){
                loadCustomerFragment(new CustomerHomeFragment());
            }
        }else{
            loadCustomerFragment(new CustomerHomeFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.customer_home:
                fragment=new CustomerHomeFragment();
                break;
            case R.id.customer_orders:
                fragment=new CustomerOrdersFragment();
                break;

            case R.id.customer_profile:
                fragment=new CustomerProfileFragment();
                break;

            case R.id.customer_shopping_cart:
                fragment=new CustomerShoppingCartFragment();
                break;

            case R.id.customer_tracker:
                fragment=new CustomerTrackerFragment();
                break;
        }
        return loadCustomerFragment(fragment);
    }

    private boolean loadCustomerFragment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container3,fragment).commit();
            return true;
        }
        return false;
    }
}