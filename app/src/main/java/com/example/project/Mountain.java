package com.example.project;

public class Mountain {
    //state goes here
    private String name;
    private String location;
    private int height;
//interface

    //default constructor
    public Mountain(){
        name="Saknar namn";
        location="Saknar plats";
        height=-2;
    }
    //Constructor that take parameters to create a new mountain
    public Mountain(String n, String l, int h){
        name=n;
        location=l;
        height=h;
    }

    public Mountain(String name) {
        this.name = name;
    }

    public String info()
    {
        String tmp=new String();
        tmp+=name+" is located in mountain "+location+"and reaches "+height+"meters above sea level.";
        return tmp;
    }
    public void setName(String n){
        name=n;
    }

    public String getName(){
        return name;
    }
    @Override
    public String toString(){
        return name;
    }
}