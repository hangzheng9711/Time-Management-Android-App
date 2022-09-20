package com.example.timemanege;

import org.litepal.crud.DataSupport;

/**
 * Created by 123 on 2017/6/2.
 */

public class LongTask extends DataSupport {

    private int id;

    private String name;

    private String starttime;

    private String endtime;

    private int  times;//小时数

    private String  period;//周期

    private int ltasklist;

    private int timelist;

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getltasklist() {
        return ltasklist;
    }

    public void setltasklist(int ltasklist) {
        this.ltasklist = ltasklist;
    }

    public int gettimelist() {
        return timelist;
    }

    public void settimelist(int timelist) {
        this.timelist = timelist;
    }

    public String getstarttime() {return starttime;}

    public void setstarttime(String starttime) {this.starttime = starttime;}

    public String getendtime() {return endtime;}

    public void setendtime(String endtime) {this.endtime = endtime;}

    public int gettimes() {
        return times;
    }

    public void settimes(int times) {
        this.times = times;
    }

    public String getperiod() {
        return period;
    }

    public void setperiod(String period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}
}
