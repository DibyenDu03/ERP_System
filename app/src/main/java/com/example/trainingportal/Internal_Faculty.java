package com.example.trainingportal;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Internal_Faculty extends Faculty_Schedule {
    private String Faculty;
    private String date;
    private DatabaseReference db;
    public Internal_Faculty(String Faculty1,String date)
    {
        this.Faculty=Faculty1;
        this.date=date;
        remove(Faculty1,date);
    }
    private void remove(String Faculty1,final String date)
    {
        db = FirebaseDatabase.getInstance().getReference("Faculty").child("Internal").child(Faculty1).child("user1");
        Log.d(TAG, "remove: "+db);
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String artist1 = dataSnapshot1.getValue(String.class);
                    if(artist1.equals(date))
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
