package com.hope.cs.fooddeliveryapp.customerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hope.cs.fooddeliveryapp.MainMenu;
import com.hope.cs.fooddeliveryapp.R;
import com.hope.cs.fooddeliveryapp.UpdateDish;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private List<UpdateDish> updateDishModelList;
    private CustomerHomeAdapter adapter;
    String State,City,Area;
    DatabaseReference dataa,databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_home, null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.cus_recycle_view);
        recyclerView.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move);
        recyclerView.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.cusSwipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                String  userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dataa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Customer custo = snapshot.getValue(Customer.class);
                        City = custo.getCity();
                        Area = custo.getArea();
                        customermenu();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        return v;
    }

    @Override
    public void onRefresh() {
        customermenu();
    }

    private void customermenu() {
        swipeRefreshLayout.setRefreshing(true);
        String  userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Customer custo = snapshot.getValue(Customer.class);
                City = custo.getCity();
                Area = custo.getArea();
                customermenu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {
        FirebaseAuth.getInstance().signOut();
        Intent logout = new Intent(getActivity(), MainMenu.class);
        logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logout);
    }
}