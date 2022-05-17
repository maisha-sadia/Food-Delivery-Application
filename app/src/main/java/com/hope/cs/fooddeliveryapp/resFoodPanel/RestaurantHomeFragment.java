package com.hope.cs.fooddeliveryapp.resFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class RestaurantHomeFragment extends Fragment {
    RecyclerView recyclerView;
    private List<UpdateDish> updateDishModelList;
    private RestaurantHomeAdapter adapter;
    DatabaseReference dataa;
    private String City,Area;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_home,null);
        getActivity().setTitle("Home");
        setHasOptionsMenu(true);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.Recycle_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataa = FirebaseDatabase.getInstance().getReference("Restaurant").child(userid);
        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Here","dataa");

                Restaurant restaurant = snapshot.getValue(Restaurant.class);
                City = restaurant.getCity();
                Area = restaurant.getArea();
                RestaurantDishes();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void RestaurantDishes() {
        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("user",useridd);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Food_Details").child(City).child(Area).child(useridd);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateDishModelList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    UpdateDish updateDishModel = snapshot1.getValue(UpdateDish.class);
                    updateDishModelList.add(updateDishModel);
                }
                adapter = new RestaurantHomeAdapter(getContext(),updateDishModelList);
                recyclerView.setAdapter(adapter);
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
