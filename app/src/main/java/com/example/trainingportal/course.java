package com.example.trainingportal;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.Window;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class course extends AsyncTask<Void,String,ArrayList<String>> {
    private String sub;
    private String date;
    private static ArrayList<String> arr;
    private static ArrayList<String> brr;
    private DatabaseReference db;
    private int flag=0;
    private Faculty_Schedule fsi,fse;
    private getFaculty gf;

    public course(String sub,String date)
    {
        arr=new ArrayList<String>();
        brr=new ArrayList<String>();
        this.sub=sub;
        this.date=date;
       // fsi=new Internal_Faculty();
       // fse=new External_Faculty();
        gf=new getFaculty();

        faculty(sub, date,gf);

    }
    private void faculty(String subject, String date, final FirebaseCallback fb)
    {
        db = FirebaseDatabase.getInstance().getReference("Course_Faculty").child(sub).child("Internal_Faculty");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arr.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String artist = dataSnapshot1.getValue(String.class);
                    // flag++;
                    //artistList.add(artist);
                    arr.add(artist);
                    publishProgress(artist);

                }

               // fsi=new Internal_Faculty(arr);

             //   course.set(arr);
//                Log.d("Checking_faculty", "onDataChange: "+arr+" "+db);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        //     Log.d("Checking_faculty", "onDataChange: "+arr+" "+db);
        db = FirebaseDatabase.getInstance().getReference("Course_Faculty").child(sub).child("External_Faculty");

        // publishProgress("Change");
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                brr.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String artist = dataSnapshot1.getValue(String.class);
                    // flag++;
                    //artistList.add(artist);
                    arr.add(artist);
                    publishProgress(artist);

                }

                //course.set(brr);
                fb.onCallback(arr);

                //  Log.d("Checking_faculty", "onDataChange: "+brr+" "+db);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }


    public ArrayList<String> get1()
    {
        return arr;
    }
    public ArrayList<String> getB()
    {
        return brr;
    }
    private static void set(ArrayList<String> s)
    {
        arr=s;
    }
    private static void setb(ArrayList<String> s)
    {
        brr=s;
    }

    @Override
    protected synchronized ArrayList<String> doInBackground(Void... voids) {
      /*  db = FirebaseDatabase.getInstance().getReference("Course_Faculty").child(sub).child("Internal_Faculty");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //arr.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String artist = dataSnapshot1.getValue(String.class);
                   // flag++;
                    //artistList.add(artist);
                   // arr.add(artist);
                    publishProgress(artist);

                }

                fsi=new Internal_Faculty(arr);
                course.set(arr);
//                Log.d("Checking_faculty", "onDataChange: "+arr+" "+db);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        //     Log.d("Checking_faculty", "onDataChange: "+arr+" "+db);
        db = FirebaseDatabase.getInstance().getReference("Course_Faculty").child(sub).child("External_Faculty");

       // publishProgress("Change");
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //brr.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String artist = dataSnapshot1.getValue(String.class);
                   // flag++;
                    //artistList.add(artist);
                    //brr.add(artist);
                    publishProgress(artist);

                }

                fse=new External_Faculty(brr);
                //course.set(brr);

                //  Log.d("Checking_faculty", "onDataChange: "+brr+" "+db);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        // Log.d("Checking_faculty", "onDataChange: "+brr+" "+db);
      //  publishProgress(arr);
       // publishProgress(brr);

       */
        return arr;
    }
    @Override
    protected void onProgressUpdate(String... voids)
    {
        super.onProgressUpdate();

        /*arr.add(voids[0]);
       // Log.d("Asyn Task", "onProgressUpdate: "+voids[0]);
        Log.d("Asyn Task", "onProgressUpdate: "+arr);

         */
    }
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

    }
    @Override
    protected synchronized void onPostExecute(ArrayList<String> res) {
        super.onPostExecute(res);
       Log.d("Asyn Task", "onPostUpdate: "+res);
        //Log.d("Asyn Task", "onProgressUpdate: "+brr);


    }
    private interface FirebaseCallback{
        ArrayList<String> onCallback(ArrayList<String> arr);


    }
    class getFaculty implements FirebaseCallback
    {

        @Override
        public ArrayList<String> onCallback(ArrayList<String> ar) {
            Log.d("Check", "onCallBack: "+ar);
            return ar;
        }

    }
}

