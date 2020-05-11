package com.example.trainingportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private String type="",user="",pass="";
    private EditText edit,edit1;
    private User user1;
    private DatabaseReference db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         spinner= findViewById(R.id.category);
         adapter= ArrayAdapter.createFromResource(this,R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type= parent.getItemAtPosition(position).toString();

    }

    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void Signin(View v)
    {
        edit=findViewById(R.id.username);
        edit1=findViewById(R.id.password);
        user=edit.getText().toString();
        pass=edit1.getText().toString();
        if(user.length()>0 && pass.length()>0)
        {
            user1=new User(pass,type,user);
            db = FirebaseDatabase.getInstance().getReference("Login_Table");

            db.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int flag=0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        User artist = dataSnapshot1.getValue(User.class);
                        if((artist.getUser1().get(1)).equals(user1.getUser1().get(1))&&(artist.getUser1().get(2)).equals(user1.getUser1().get(2))&&(artist.getUser1().get(0)).equals(user1.getUser1().get(0)))
                        {

                            flag++;
                        }

                    }
                    if(flag>=1)
                    {
                        Toast.makeText(MainActivity.this,"Sucess!!! ", Toast.LENGTH_SHORT).show();
                        if(type.equals("Coordinator"))
                        {
                            Intent in=new Intent(MainActivity.this,Coordinator.class);
                            in.putExtra("USER_NAME", user);
                            startActivity(in);
                        }
                        else
                        {
                            Intent in=new Intent(MainActivity.this,Department.class);
                            in.putExtra("USER_NAME", user);
                            in.putExtra("Valid","No");
                            startActivity(in);
                        }

                    }
                    else{
                        Toast.makeText(MainActivity.this,"Username or Password was worng", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });

        }
        else
        {
            Toast.makeText(MainActivity.this,"Username or Password field should not be blanked", Toast.LENGTH_SHORT).show();
        }
    }

}
