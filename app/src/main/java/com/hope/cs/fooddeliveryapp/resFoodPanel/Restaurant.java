package com.hope.cs.fooddeliveryapp.resFoodPanel;

public class Restaurant {
 private String Area,City,ConfirmPassword,EmailID,Forename,Surname,HouseNumber,PhoneNumber,PostCode;

    public Restaurant(String area, String city, String confirmPassword, String emailID, String forename, String surname, String houseNumber, String phoneNumber, String postCode) {
        this.Area = area;
        City = city;
        ConfirmPassword = confirmPassword;
        EmailID = emailID;
        Forename = forename;
        Surname = surname;
        HouseNumber = houseNumber;
        PhoneNumber = phoneNumber;
        PostCode = postCode;
    }

    public Restaurant(){

    }
    public String getArea() {
        return Area;
    }

    public String getCity() {
        return City;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public String getEmailID() {
        return EmailID;
    }

    public String getForename() {
        return Forename;
    }

    public String getSurname() {
        return Surname;
    }

    public String getHouseNumber() {
        return HouseNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getPostCode() {
        return PostCode;
    }
}
