package com.hope.cs.fooddeliveryapp.resFoodPanel;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.hope.cs.fooddeliveryapp.R;

public class RestaurantProfileFragment extends Fragment {
    Button postDish;
    ConstraintLayout backGroundImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_profile,null);
        getActivity().setTitle("Post New Dish");
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bg_image),3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.chips_image),3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.starting_bg_image),3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.burger_image),3000);

        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);

        backGroundImage = view.findViewById(R.id.res_home_bg);
        backGroundImage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        postDish = (Button)view.findViewById(R.id.post_dish);
        postDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),restaurantPostDish.class));

            }
        });

        return view;
    }
}
