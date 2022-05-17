package com.hope.cs.fooddeliveryapp.customerFoodPanel;

public class Customer {
    private String city,FirstName,LastName,Password,ConfirmPassword,EmailId,MobileNo,Area,LocalAddress;

    public Customer(String city, String firstName, String lastName, String password, String confirmPassword, String emailId, String mobileNo, String area, String localAddress) {
        this.city = city;
        FirstName = firstName;
        LastName = lastName;
        Password = password;
        ConfirmPassword = confirmPassword;
        EmailId = emailId;
        MobileNo = mobileNo;
        Area = area;
        LocalAddress = localAddress;
    }
    public Customer(){

    }

    public String getCity() {
        return city;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPassword() {
        return Password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public String getEmailId() {
        return EmailId;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getArea() {
        return Area;
    }

    public String getLocalAddress() {
        return LocalAddress;
    }
}
