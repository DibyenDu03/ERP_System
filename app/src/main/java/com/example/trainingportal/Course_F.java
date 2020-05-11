package com.example.trainingportal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Course_F extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String sub;
    private String date;
    private static ArrayList<String> arr;
    private static ArrayList<String> brr;
    private DatabaseReference db;
    private int flag=0;
    private Faculty_Schedule fs;
    private String depart;
    private static ListView list;
    private List<Course_check> artistList;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private int flaggy=0;
    private AlertDialog.Builder build;
    private Schedule sc;
    private String sch;
    private Process_Request pr;
    private int flag1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__f);
        arr=new ArrayList<String>();
        brr=new ArrayList<String>();
        //arr.add("No Faculty is Availble");
        //brr.add("No Faculty is Availble");
        Intent in=getIntent();
        build = new AlertDialog.Builder(this);
        depart= in.getStringExtra("User");
        sub=in.getStringExtra("Subject");
        date=in.getStringExtra("Date");
        sch=in.getStringExtra("query");
        Log.d("Checking", "onCreate: "+sub+" "+date+" "+depart);
        list = (ListView) findViewById(R.id.list2);
        flaggy=0;
        artistList = new ArrayList<>();
        spinner= findViewById(R.id.faculty);
        adapter= ArrayAdapter.createFromResource(this,R.array.faculty, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(flaggy==1)
                {

                        String[] s=sch.trim().split(" ");
                       sc=new Schedule(s[2]+" "+s[3],s[1],s[0],brr.get(position));
                       pr=new Process_Request(s[2]+" "+s[3],s[1],s[0]);
                       message("External",brr.get(position));
                        Toast.makeText(getApplicationContext(),brr.get(position),Toast.LENGTH_LONG).show();

                }
                else
                {
                        fs=new External_Faculty();
                        String[] s=sch.trim().split(" ");
                        sc=new Schedule(s[2]+" "+s[3],s[1],s[0],arr.get(position));
                        pr=new Process_Request(s[2]+" "+s[3],s[1],s[0]);
                        message("Internal",arr.get(position));
                        Toast.makeText(getApplicationContext(),arr.get(position),Toast.LENGTH_LONG).show();
                }


            }

        });


    }
    public void onStart() {
        super.onStart();


        if(flaggy==1)
        {
            faculty(sub, date,"External");

        }
        else
        {
            faculty(sub, date);
        }
    }

    private void faculty(String subject, final String date,final String s)
    {
        brr.clear();
        db = FirebaseDatabase.getInstance().getReference("Course_Faculty").child(subject).child("External_Faculty");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final String artist = dataSnapshot1.getValue(String.class);




                    DatabaseReference db1;
                    db1=FirebaseDatabase.getInstance().getReference("Faculty").child(s).child(artist).child("user1");


                    {
                        db1.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    String artist1 = dataSnapshot1.getValue(String.class);

                                    if(artist1.equals(date))
                                    {
                                        brr.add(artist);
                                    }
                                }
                                Course_check cd=new Course_check(brr);
                                List<String> arr1=cd.getUser1();
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Course_F.this, android.R.layout.simple_list_item_1, arr1 );
                                list.setAdapter(arrayAdapter);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {


                            }
                        });
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }
    private void message(final String s,final String s1)
    {
        //Uncomment the below code to Set the message and title from the strings.xml file
        build.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        build.setMessage("Do you want to assign this Faculty").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if(s.equals("External"))
                            fs=new External_Faculty(s1,date);
                        else
                            fs=new Internal_Faculty(s1,date);

                        Intent in=new Intent(Course_F.this,Coordinator.class);
                        in.putExtra("USER_NAME", depart);
                        startActivity(in);

                        Toast.makeText(getApplicationContext(),"you choose yes action",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(getApplicationContext(),"you choose no action",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = build.create();
        //Setting the title manually
        alert.setTitle("Choose the Faculty");
        adapter.notifyDataSetChanged();
        alert.show();
    }


    private void faculty(String subject, final String date)
    {

        arr.clear();
        db = FirebaseDatabase.getInstance().getReference("Course_Faculty").child(subject).child("Internal_Faculty");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    final String artist = dataSnapshot1.getValue(String.class);


                    DatabaseReference db1;
                    db1=FirebaseDatabase.getInstance().getReference("Faculty").child("Internal").child(artist).child("user1");


                    {
                        db1.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    String artist1 = dataSnapshot1.getValue(String.class);

                                    if(artist1.equals(date))
                                    {
                                        arr.add(artist);
                                    }
                                }
                                List<String> arr1=arr;
                                //Log.d("Checking ", "onDataChange: "+arr1);
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Course_F.this, android.R.layout.simple_list_item_1, arr1 );
                                list.setAdapter(arrayAdapter);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {


                            }
                        });
                    }



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    public void Refresh(View v)
    {
        if(flaggy==1)
        {
            faculty(sub, date,"External");

        }
        else
        {
            faculty(sub, date);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position==1)
        {

            flaggy=1;
            if(flag1==0)
            {
                faculty(sub, date,"External");
                flag1++;
            }

            Course_check cd=new Course_check(brr);
            List<String> arr1=cd.getUser1();
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Course_F.this, android.R.layout.simple_list_item_1, arr1 );
            list.setAdapter(arrayAdapter);


        }
        else
        {
            flaggy=0;
            List<String> arr1=arr;
            //Log.d("Checking ", "onDataChange: "+arr1);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Course_F.this, android.R.layout.simple_list_item_1, arr1 );
            list.setAdapter(arrayAdapter);


        }
    }

    public void Remove(View v)
    {
        String[] s=sch.trim().split(" ");
        pr=new Process_Request(s[2]+" "+s[3],s[1],s[0]);
        Intent in=new Intent(Course_F.this,Coordinator.class);
        in.putExtra("USER_NAME", depart);
        startActivity(in);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


