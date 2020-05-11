package com.example.trainingportal;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Schedule {
    private String date,depart,subject,faculty;
    private DatabaseReference db;
    private ArrayList<String> user1;
    private FirebaseAuth mAuth;
    private User user;
    public Schedule(String date,String depart,String subject,String faculty)
    {
        this.date=date;
        user1=new ArrayList<String>();
        this.subject=subject;
        this.faculty=faculty;
        this.depart=depart;
        user1.add(date);
        user1.add(depart);
        user1.add(subject);
        user1.add(faculty);
        user=new User(user1);
        upload();
    }
    private void upload(){
        int s=(int)(Math.random()*1000000000);
        String add=String.valueOf(s);
        String[] ad=add.split(".");
        String fam="friends"+add;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Schedule");
        myRef.child(fam).setValue(user);
    }
}
