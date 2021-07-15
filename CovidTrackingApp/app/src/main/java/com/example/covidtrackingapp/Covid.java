package com.example.covidtrackingapp;

public class Covid {

    private int totalcases;
    private int totalactive;
    private int totaldeaths;


    public Covid(int totalcases , int totalactive , int totaldeaths)
    {
        this.totalcases = totalcases;
        this.totalactive = totalactive;
        this.totaldeaths = totaldeaths;
    }
    public int getmtotalcount() {
        return totalcases;
    }

    public int getmtotalactive() {
        return totalactive;
    }

    public int getmtotaldeaths() {
        return totaldeaths;
    }

}
