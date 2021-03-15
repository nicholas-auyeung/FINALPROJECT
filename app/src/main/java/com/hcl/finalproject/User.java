package com.hcl.finalproject;

public class User {

    private long id;
    private String username;
    private String email;
    public Address address;
    public Address getAddress(){
        return address;
    }
    public class Address {

    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                '}';
    }
}
