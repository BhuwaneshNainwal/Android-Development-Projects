package com.example.covidtrackingapp;

public class Covid {

    private int cases;
    private int active;
    private int deaths;
    private int recovery;
    private String state;

    public Covid(String state , int cases , int active , int deaths , int recovery)
    {
        this.cases = cases;
        this.active = active;
        this.deaths = deaths;
        this.recovery = recovery;
        this.state = state;
    }
    public int getmtotalcount() {
        return cases;
    }

    public int getmtotalactive() {
        return active;
    }

    public int getmtotaldeaths() {
        return deaths;
    }


    public int getmrecovery() {
        return recovery;
    }
    public String getmstate() {
        return state;
    }

}
