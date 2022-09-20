package com.example.timemanege;

import org.litepal.crud.DataSupport;

/**
 * Created by 123 on 2017/6/5.
 */

public class TimeList extends DataSupport {

    private int id;

    private int taskid;

    private String name;

    private String time_start;

    private String time_end;

    private long m_start;

    private long m_end;

    private int s_or_l;

    public int getid() {
        return id;
    }

    public void setid(int id) {this.id = id;}

    public void setm_start(long m_start) {this.m_start = m_start;}

    public long getm_start() {
        return m_start;
    }

    public void setm_end(long m_end) {this.m_end = m_end;}

    public long getm_end() {
        return m_end;
    }

    public int gets_or_l() {
        return s_or_l;
    }

    public void sets_or_l(int s_or_l) {this.s_or_l = s_or_l;}

    public int gettaskid() {
        return taskid;
    }

    public void settaskid(int taskid) {
        this.taskid = taskid;
    }

    public String gettime_start() {return time_start;}

    public void settime_start(String time_start) { this.time_start = time_start;}

    public String gettime_end() {return time_end;}

    public void settime_end(String time_end) {this.time_end = time_end;}

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}
}
