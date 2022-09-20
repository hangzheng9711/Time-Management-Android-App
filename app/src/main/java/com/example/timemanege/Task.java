package com.example.timemanege;

/**
 * Created by Zhangchi on 2017/6/3.
 */

public class Task {
    public int tasknum;
    public String taskname;
    public String starttime;
    public String endtime;
    public String taskcolor;
    public int isover;
    public int isdelete;


    public Task(int num, String name, String stime, String etime, String color,int over,int delete){
        this.tasknum = num;
        this.taskname = name;
        this.starttime = stime;
        this.endtime = etime;
        this.taskcolor = color;
        this.isover = over;
        this.isdelete = delete;
    }
    public String getTaskName(){
        return this.taskname;
    }


}
