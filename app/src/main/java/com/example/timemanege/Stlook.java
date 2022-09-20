package com.example.timemanege;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2017/6/2 0002.
 */

public class Stlook extends AppCompatActivity {

    Chronometer timer;
    Button button_sta;

    Button store_file;
    Boolean chronometerState;
    TextView stlook_taskname;
    long mRecordTime;
    int tid;

    ShortTask task;


    Button stlook_taskover;
    Button stlook_stasklist;
    Button stlook_edit;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stlook);

        Intent intent=getIntent();
        int id = intent.getIntExtra("extra_id",-1);


        task= DataSupport.find(ShortTask.class,id);

        stlook_taskname = (TextView) findViewById(R.id.stlook_taskname);

        stlook_taskname.setText(task.getName());

        stlook_taskover = (Button) findViewById(R.id.stloook_over);
        stlook_edit = (Button) findViewById(R.id.stlook_edit);
        stlook_stasklist = (Button) findViewById(R.id.stlook_delete);
        stlook_taskover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stlook_taskover.setSelected(true);
                stlook_taskover.setEnabled(false);
                ContentValues values = new ContentValues();
                task.setfinish(1);
                task.update(task.getid());
                Intent intent = new Intent(Stlook.this, First.class);
                startActivity(intent);
            }
        });
        stlook_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Stlook.this, editsec.class);
                intent.putExtra("extra_id",task.getid());

                startActivity(intent);
            }
        });
        stlook_stasklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                switch (v.getId()){
                    case R.id.stlook_delete:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Stlook.this);
                        dialog.setMessage("你要从任务列表中删除它吗？");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("果断删除",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,int which){
                                task.setToDefault("stasklist");
                                task.update(task.getid());
                                Intent intent = new Intent(Stlook.this, First.class);
                                Toast.makeText(Stlook.this,"删除成功",Toast.LENGTH_SHORT).show();


                                startActivity(intent);
                            }
                        });
                        dialog.setNegativeButton("我再考虑一下",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,int which){

                            }
                        });
                        dialog.show();
                        break;
                    default:
                        break;
                }


            }
        });

        final Time curTime;
        chronometerState = false;
        mRecordTime=0;
        timer = (Chronometer) findViewById(R.id.timer);
        timer.setFormat("%s");
        final ImageView button_sta= (ImageView) this.findViewById(R.id.start);
        button_sta.setImageResource(R.drawable.start);
        store_file=(Button)findViewById(R.id.store_file);
        store_file.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                task.setToDefault("stasklist");
                task.setrecord(1);
                task.updateAll("id = ?",task.getid()+"");
                Intent intent2 = new Intent(Stlook.this, First.class);
                startActivity(intent2);
            }
        });

        button_sta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TimeList tasktime=new TimeList();
                if(chronometerState){
                    mRecordTime = SystemClock.elapsedRealtime();
                    chronometerState = false;
                    button_sta.setImageResource(R.drawable.start);

                    timer.stop();
                    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm");
                    SimpleDateFormat   formatter1   =   new SimpleDateFormat("HH:mm");

                    Date   curDate   =   new   Date(System.currentTimeMillis());//获取当前时间
                    String   str   =   formatter.format(curDate);
                    String str1=formatter1.format(curDate);
                    char str11[]=str1.toCharArray();
                    long a=((str11[0]-'0')*10+(str11[1]-'0'))*60+(str11[3]-'0')*10+(str11[4]-'0');

                    tasktime.settime_end(str);
                    tasktime.setm_end(a);
                    //     Toast.makeText(Stlook.this,str,Toast.LENGTH_SHORT).show();
                    tasktime.updateAll("id=?",tid+"");
                    //         tasktime.save();
                    //        Toast.makeText(Stlook.this,tasktime.getid()+"",Toast.LENGTH_SHORT).show();
                    List<TimeList> tasktimes = DataSupport.where("id= ?",tid+"").find(TimeList.class);
//                   for(TimeList tasktime1:tasktimes)
//                    {
//                       Toast.makeText(Stlook.this,tasktime1.getid()+"",Toast.LENGTH_SHORT).show();
//                       Toast.makeText(Stlook.this,tasktime1.getName()+"",Toast.LENGTH_SHORT).show();
//                       Toast.makeText(Stlook.this,tasktime1.gettime_start(),Toast.LENGTH_SHORT).show();
//                       Toast.makeText(Stlook.this,tasktime1.gettime_end(),Toast.LENGTH_SHORT).show();
//                  }

                }
                else{
                    chronometerState = true;
                    if (mRecordTime != 0) {
                        timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                    } else {
                        timer.setBase(SystemClock.elapsedRealtime());
                    }
                    int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 60);
                    timer.setFormat("0"+String.valueOf(hour)+":%s");
                    button_sta.setImageResource(R.drawable.stop);
                    timer.start();
//                    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
//                    Date curDate = new Date(System.currentTimeMillis());//获取当前系统日期
//                    String currDate = dateformatter.format(curDate);//将系统日期按自定义格式转换为String

                    SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd  HH:mm");
                    SimpleDateFormat   formatter1   =   new SimpleDateFormat("HH:mm");
                    Date   curDate   =   new   Date();//获取当前时间
                    String   str   =   formatter.format(curDate);
                    String str1=formatter1.format(curDate);
                    char str11[]=str1.toCharArray();
                    long a=((str11[0]-'0')*10+(str11[1]-'0'))*60+(str11[3]-'0')*10+(str11[4]-'0');

                    tasktime.settime_start(str);
                    tasktime.setName(task.getName());
                    tasktime.settaskid(task.getid());
                    tasktime.setToDefault("s_or_l");
                    tasktime.setm_start(a);
                    tasktime.save();
                    tid=tasktime.getid();
                    //     Toast.makeText(Stlook.this,SystemClock.elapsedRealtime()+"",Toast.LENGTH_SHORT).show();
//
//                    Toast.makeText(Stlook.this,tid+"",Toast.LENGTH_SHORT).show();

                }





            }
        });








    }
}