package com.example.trainingportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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

public class Report extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Course_check user1;
    private DatabaseReference db;
    private ListView list;
    private List<Course_check> artistList;
    private ArrayList<String> arr;
    private DatePicker picker;
    private int pos=0;
    private String depart;
    private ArrayList<Integer> faculty = new ArrayList<>();
    private Spinner spinner,hideSpinner;
    private ArrayAdapter<CharSequence> adapter,adapter1,adapter2;
    private int flaggy=0;
    final private String[] subject={"JAVA","DJANGO","ORACLE","PHP","PYTHON"};
    final private String[]  depar={"CSIT","ECE","EEE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_report);
        Intent intent = getIntent();
        depart= intent.getStringExtra("USER_NAME");
        list = (ListView) findViewById(R.id.list19034);
        artistList = new ArrayList<>();
        arr=new ArrayList<String>();
        flaggy=0;
        spinner= findViewById(R.id.report);
        hideSpinner= findViewById(R.id.report2);
        hideSpinner.setEnabled(false);
        adapter= ArrayAdapter.createFromResource(this,R.array.report, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter1= ArrayAdapter.createFromResource(this,R.array.Department, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2= ArrayAdapter.createFromResource(this,R.array.subject, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            }

        });
    }
    public void onStart()
    {
        super.onStart();

        db = FirebaseDatabase.getInstance().getReference("Schedule");

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
                    arr=sort(arr);
                    Course_check cd=new Course_check(arr);
                    List<String> arr1=cd.getUser1();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Report.this, android.R.layout.simple_list_item_1, arr1 );
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
                /*Intent in=new Intent(getApplicationContext(),Course_F.class);
                String date=arr.get(position).trim().split(" ")[2]+" "+arr.get(position).trim().split(" ")[3];
                in.putExtra("User",depart);
                in.putExtra("Subject",str);
                in.putExtra("Date",date);
                in.putExtra("query",arr.get(position));
                Log.d("Checking", "onCreate: "+str+" "+date);
                startActivity(in);
                // cou.execute();

                 */
                pos=position;

            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         flaggy=position;
            if(flaggy==0)
            {
                hideSpinner.setEnabled(false);
                db = FirebaseDatabase.getInstance().getReference("Schedule");

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
                            arr=sort(arr);
                            Course_check cd=new Course_check(arr);
                            List<String> arr1=cd.getUser1();
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Report.this, android.R.layout.simple_list_item_1, arr1 );
                            list.setAdapter(arrayAdapter);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });

            }

            if(flaggy==1)
            {
                hideSpinner.setEnabled(true);
                hideSpinner.setAdapter(adapter1);
                hideSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                    {
                        //Toast.makeText(Report.this,position,Toast.LENGTH_LONG).show();
                       Show(subject[position],1);
                    }

                    public void onNothingSelected(AdapterView<?> parentView)
                    {

                    }
                });

            }

            if(flaggy==2)
            {
                hideSpinner.setEnabled(true);
                hideSpinner.setAdapter(adapter2);
                hideSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
                    {
                        Show(depar[position],0);
                    }

                    public void onNothingSelected(AdapterView<?> parentView)
                    {

                    }
                });
            }



    }
    public void Show(final String str,final int s)
    {
        db = FirebaseDatabase.getInstance().getReference("Schedule");

        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arr.clear();
                int flag=0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Course_check artist = dataSnapshot1.getValue(Course_check.class);
                    flag++;
                    //artistList.add(artist);
                    String[] dep=artist.get().split(" ");
                    Log.d("Checking", "onDataChange: "+dep[3]+" "+str);
                    if(dep[3].equals(str)&&s==0)
                        arr.add(artist.get());
                    if(dep[4].equals(str)&&s==1)
                        arr.add(artist.get());

                }
                if(flag>0)
                {
                    arr=sort(arr);
                    Course_check cd=new Course_check(arr);
                    List<String> arr1=cd.getUser1();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Report.this, android.R.layout.simple_list_item_1, arr1 );
                    list.setAdapter(arrayAdapter);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    protected ArrayList<String> sort(ArrayList<String> data)
    {
        int len_data = data.size();
        String dates[] = new String[len_data];
        for(int i = 0; i< len_data; i++)
        {
            String curr = data.get(i).trim();
            String[] mod = curr.split(" ");
            dates[i] = mod[0];
        }

        int n = len_data;
        for(int i=0; i < n; i++)
        {
            for(int j=1; j < (n-i); j++)
            {


                String[] s = dates[j-1].split("-");
                Log.d("Array display", "sort: "+s[0]);
                if(s.length>2)
                {
                    String s1 = s[2] + '-' + s[1] + '-' + s[0];
                    s = dates[j].split("-");
                    String s2 = s[2] + '-' + s[1] + '-' + s[0];
                    int res = s1.compareTo(s2);

                    if(res > 0)
                    {
                        String temp = dates[j-1];
                        dates[j-1] = dates[j];
                        dates[j] = temp;

                        temp = data.get(j-1);
                        data.set(j-1, data.get(j));
                        data.set(j, temp);

                    }
                }


            }
        }


        return data;
    }
}
