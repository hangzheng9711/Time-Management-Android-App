package com.example.timemanege;

import org.litepal.crud.DataSupport;

public class ShortTask extends DataSupport {

    private int id;

    private String name;

    private String starttime;

    private String endtime;

    private int  stasklist;

    private int  record;

    private int timelist;

    private int finish;

//    public ShortTask(){ }
//
//  public  ShortTask(int tasknum,String taskname,String starttime,String endtime,int finish,int stasklist,int timelist,int record)
//  {
//      this.id = tasknum;
//      this.name=taskname;
//      this.starttime=starttime;
//      this.endtime=endtime;
//      this.finish=finish;
//      this.stasklist=stasklist;
//      this.timelist=timelist;
//      this.record=record;
//  }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getstarttime() {return starttime;}

    public void setstarttime(String starttime) { this.starttime = starttime;}

    public String getendtime() {return endtime;}

    public void setendtime(String endtime) {this.endtime = endtime;}

    public int getfinish() {
        return finish;
    }

    public void setfinish(int finish) {
        this.finish = finish;
    }

    public int gettimelist() {
        return timelist;
    }

    public void settimelist(int timelist) {this.timelist = timelist;}

    public int getstasklist() {
        return stasklist;
    }

    public void setstasklist(int stasklist) {
        this.stasklist = stasklist;
    }

    public int getrecord() {
        return record;
    }

    public void setrecord(int record) {
        this.record = record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}


}
