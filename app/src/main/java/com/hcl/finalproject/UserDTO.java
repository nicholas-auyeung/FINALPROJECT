package com.hcl.finalproject;

import android.util.Log;

import java.util.concurrent.Semaphore;

public class UserDTO {

    private static UserDTO SingletonHolder_INSTANCE = null;

    private User[] users;

    private UserDTO(){
    }

    /*
    private static class SingletonHolder{
        public static final UserDTO INSTANCE = new UserDTO();
    }

    public static UserDTO getInstance(){
        return UserDTO.SingletonHolder.INSTANCE;
    }
     */

    public static UserDTO getInstance(){
        if (SingletonHolder_INSTANCE == null) {
            SingletonHolder_INSTANCE = new UserDTO();
        }
        return SingletonHolder_INSTANCE;
    }


    public User[] getUserDTO(){
        for (User u : this.users){
            Log.i("USER IN DTO IN GET", u.toString());
        }
        return this.users;
    }

    public void setUserDTO(User[] users){
        this.users = new User[users.length];
        System.arraycopy(users, 0, this.users, 0, this.users.length);

        for (User u : this.users){
            Log.i("USER IN DTO", u.toString());
        }
    }



}
