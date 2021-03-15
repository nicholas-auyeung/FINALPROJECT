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
