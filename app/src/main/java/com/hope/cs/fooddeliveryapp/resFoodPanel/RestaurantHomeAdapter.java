package com.hope.cs.fooddeliveryapp.resFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hope.cs.fooddeliveryapp.R;
import com.hope.cs.fooddeliveryapp.UpdateDish;
import com.hope.cs.fooddeliveryapp.activity_update_delete_dish;

import java.text.BreakIterator;
import java.util.List;

public class RestaurantHomeAdapter extends RecyclerView.Adapter<RestaurantHomeAdapter.ViewHolder> {
    private Context mcont;
    private List<UpdateDish> updateDishModelList;

    public RestaurantHomeAdapter(Context context , List<UpdateDish> updateDishModelList){
        this.updateDishModelList = updateDishModelList;
        this.mcont = context;
    }

    @NonNull
    @Override
    public RestaurantHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.restaurant_update_delete,parent,false);
        return new RestaurantHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHomeAdapter.ViewHolder holder, int position) {
        final UpdateDish updateDishModel = updateDishModelList.get(position);
        holder.dishes.setText(updateDishModel.getDishes());
        updateDishModel.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcont, activity_update_delete_dish.class);
                intent.putExtra("updatedeletedish",updateDishModel.getRandomUID());
                mcont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateDishModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dishes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishes = itemView.findViewById(R.id.res_item_name);
        }
    }
}
