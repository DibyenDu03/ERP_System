package com.example.trainingportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Validate_request extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView ed;
    private String str1,str2,str3;
    private String date;
    private DatabaseReference db;
    private FirebaseAuth mAuth;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_request);
        ed=findViewById(R.id.edit12);
        Intent in=getIntent();
        str1=in.getStringExtra("User");
        str2=in.getStringExtra("Date");
        str3=in.getStringExtra("Course");
        String comp = "";
        String s = str2;
        String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        int i = s.indexOf(',');
        System.out.println("Index is:" + i);
        String x = s.substring(i+2);
        System.out.println(x);
        int s1 = x.indexOf(' ');
        String date = x.substring(0,s1);
        System.out.println(date);
        int temp = date.length();
        if(temp == 1)
        {
            comp = comp + '0' + date + '-';
        }
        else
        {
            comp = comp + date + '-';
        }

        x = x.substring(s1+1);
        int s2 = x.indexOf(' ');
        String m = x.substring(0,s2);

        int j=0;
        for(j=0;j<12;j++)
        {
            if(month[j].equals(m))
            {
                break;
            }
        }
        int mth = j + 1;
        String mnth = Integer.toString(mth);
        if(mnth.length() == 1)
        {
            comp = comp + '0' + mnth + '-';
        }
        else
        {
            comp = comp + mnth + '-';
        }

        String year = x.substring(s2+1);
        comp = comp + year;
        str2=comp;
        ed.setText("\n Course_name-\t "+str3+"\nDate-\t "+str2);
        spinner= findViewById(R.id.slot);
        adapter= ArrayAdapter.createFromResource(this,R.array.Slot, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type= parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void update(View v)
    {
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Pending");
        int s=(int)(Math.random()*1000000000);
        String add=String.valueOf(s);
        String[] ad=add.split(".");
        String fam="friends"+add;
        if(type.equals("9-12"))
        {
            type="A";
        }
        else
        {
            type="B";
        }
        User artist=new User(str2+" "+type,str3,str1);
        myRef.child(fam).setValue(artist);
        Intent in=new Intent(getApplicationContext(),Department.class);
        in.putExtra("USER_NAME",str1);
        in.putExtra("Valid","Yes");
       // Toast.makeText(Validate_request.this,"Request has been submitted",Toast.LENGTH_LONG);
        startActivity(in);

    }
}
