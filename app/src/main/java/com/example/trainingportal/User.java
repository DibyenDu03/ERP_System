package com.example.trainingportal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private ArrayList<String> user1;
    public User()
    {

    }
    public User(String password,String type,String user)
    {
        user1=new ArrayList<String>();

        user1.add(type);
        user1.add(user);
        user1.add(password);

    }
    public User(ArrayList<String> med1) {
        user1=med1;
    }

    public ArrayList<String> getUser1() {
        return user1;
    }

}
