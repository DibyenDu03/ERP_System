package com.example.trainingportal;

import java.util.ArrayList;

public class Course_check {
    private ArrayList<String> user1;
    public Course_check()
    {

    }
    public Course_check(String password,String type,String user)
    {
        user1=new ArrayList<String>();

        user1.add(type);
        user1.add(user);
        user1.add(password);

    }
    public Course_check(ArrayList<String> med1) {
        user1=med1;
    }

    public ArrayList<String> getUser1() {
        return user1;
    }
    public String get()
    {
        String tmp="";
        for(int i=0;i<user1.size();i++)
        {
                tmp=tmp+" "+user1.get(i);
        }
        return tmp;
    }

}
