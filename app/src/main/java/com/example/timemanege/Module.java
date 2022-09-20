package com.example.timemanege;

import org.litepal.crud.DataSupport;

/**
 * Created by 123 on 2017/6/2.
 */

public class Module extends DataSupport {
    private String name;
    private int mdlist;
    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}
    public int getMdlist() { return mdlist;}
    public void setMdlist( ){ this.mdlist=mdlist;}
}
