package com.example.trainingportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Coordinator extends AppCompatActivity  {

    private Course_check user1;
    private DatabaseReference db;
    private ListView list;
    private List<Course_check> artistList;
    private ArrayList<String> arr;
    private DatePicker picker;
    private int pos=0;
    private String depart;
    private ArrayList<Integer> faculty = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_coordinator2);
        Intent intent = getIntent();
        depart= intent.getStringExtra("USER_NAME");
        list = (ListView) findViewById(R.id.list1);
        artistList = new ArrayList<>();
        arr=new ArrayList<String>();
    }
    public void onStart()
    {
        super.onStart();

        db = FirebaseDatabase.getInstance().getReference("Pending");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arr.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Course_check artist = dataSnapshot1.getValue(Course_check.class);
                    flag++;
                    //artistList.add(artist);
                    arr.add(artist.get());

                }
                if(flag>0)
                {
                    Course_check cd=new Course_check(arr);
                    List<String> arr1=cd.getUser1();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Coordinator.this, android.R.layout.simple_list_item_1, arr1 );
                    list.setAdapter(arrayAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String str=arr.get(position).trim().split(" ")[0];
               // cou=new course(str,);
                //Log.d("Checking_Fac","Faculty Availble "+cou.get());
                Intent in=new Intent(getApplicationContext(),Course_F.class);
                String date=arr.get(position).trim().split(" ")[2]+" "+arr.get(position).trim().split(" ")[3];
                in.putExtra("User",depart);
                in.putExtra("Subject",str);
                in.putExtra("Date",date);
                in.putExtra("query",arr.get(position));
                Log.d("Checking", "onCreate: "+str+" "+date);
                startActivity(in);
               // cou.execute();
                pos=position;

            }

        });
    }
    public void Report(View v)
    {
        Intent in=new Intent(getApplicationContext(),Report.class);
        startActivity(in);

    }
    public void logout(View v)
    {
        Intent in=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
    }

}
