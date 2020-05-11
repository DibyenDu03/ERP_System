package com.example.trainingportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Department extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Course_check user1;
    private DatabaseReference db;
    private ListView list;
    private List<Course_check> artistList;
    private DatePicker picker;
    private int pos=0;
    private String depart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        depart= intent.getStringExtra("USER_NAME");
        setContentView(R.layout.activity_department);
        list = (ListView) findViewById(R.id.list);
        String check=intent.getStringExtra("Valid");
        if(check.equals("Yes"))
        {
            Toast.makeText(Department.this,"Request has been submitted",Toast.LENGTH_LONG).show();
        }
        artistList = new ArrayList<>();
    }
    public void onStart()
    {
        super.onStart();

        db = FirebaseDatabase.getInstance().getReference("Course");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistList.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Course_check artist = dataSnapshot1.getValue(Course_check.class);
                    flag++;
                    artistList.add(artist);

                }
                if(flag>0)
                {
                   List<String> arr=artistList.get(0).getUser1();
                   ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Department.this, android.R.layout.simple_list_item_1, arr );
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
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
                pos=position;

            }

    });
    }
    public void ScheduleCourse(View v)
    {
        Intent in=new Intent(Department.this,department_report.class);
        in.putExtra("USER_NAME", depart);
        in.putExtra("Valid","No");
        startActivity(in);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Intent in=new Intent(getApplicationContext(),Validate_request.class);
        in.putExtra("User",depart);
        in.putExtra("Course",artistList.get(0).getUser1().get(pos));
        in.putExtra("Date",currentDateString);

        startActivity(in);
       // Toast.makeText(Department.this,"Selected item is "+artistList.get(0).getUser1().get(pos)+" "+currentDateString,Toast.LENGTH_LONG).show();

    }
    public void logout(View v)
    {
        Intent in=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(in);
    }
}
