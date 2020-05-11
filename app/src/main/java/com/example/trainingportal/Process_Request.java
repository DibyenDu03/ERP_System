package com.example.trainingportal;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Process_Request {
    private String date,depart,subject;
    private ArrayList<String> user1;
    private FirebaseAuth mAuth;
    private DatabaseReference db;
    private User user;

    public Process_Request(String date,String depart,String subject)
    {
        this.date=date;
        user1=new ArrayList<String>();
        this.subject=subject;
        this.depart=depart;

        user1.add(subject);

        user1.add(depart);

        user1.add(date);
        user=new User(user1);
        Processit();
    }

    private void Processit(){

        db = FirebaseDatabase.getInstance().getReference("Pending");
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    User artist1 = dataSnapshot1.getValue(User.class);
                    ArrayList<String> u=artist1.getUser1();
                    ArrayList<String>v=user1;
                    if(u.get(0).equals(v.get(0))&&u.get(2).equals(v.get(2))&&u.get(1).equals(v.get(1)))
                    {
                        dataSnapshot1.getRef().removeValue();
                        break;

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

}
