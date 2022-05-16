package com.hope.cs.fooddeliveryapp.resFoodPanel;

public class Food_Details {
    public String Dishes, Quantity, Price, Description, ImageURL, RandomUUID,RestaurantID;

    public Food_Details(String dishes, String quantity, String price, String description, String imageURL, String randomUUID, String restaurantID) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUUID = randomUUID;
        RestaurantID = restaurantID;

    }
}
