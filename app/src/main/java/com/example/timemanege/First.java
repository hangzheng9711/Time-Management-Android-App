package com.example.timemanege;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class First extends AppCompatActivity {
    Button Button1;
    Button Button2;
    Button Button3;
    Button Stadd;//添加任务按钮
    // Button bt_ok;

    Button calbtn;//日历按钮
    int mYear, mMonth, mDay;
    TextView dateToday;

    final int DATE_DIALOG = 1;
    ListView stTaskListView;
    private List<ShortTask> taskList = new ArrayList<>();
    Intent intent;

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
  //  private PreferencesUtils preferencesUtils;

    //private MyDB dbHelper;

   private SQLiteDatabase db;

   public First(){ db= Connector.getDatabase();}

    @Override
    protected void onResume(){
        super.onResume();
        taskList.clear();
        stTaskListView = (ListView) findViewById(R.id.sttask_listview);
        inittask();
        taskadapter adapter = new taskadapter(First.this, R.layout.task_item, taskList);
        stTaskListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//重写函数 显示menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.background_item:
                Toast.makeText(this, "变换背景", Toast.LENGTH_LONG).show();//跳转背景
                break;
            case R.id.help_item:
                Toast.makeText(this, "获取帮助", Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return true;
    }//实现menu点击显示的功能

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_1);//和layout建立联系
        Button1 = (Button) findViewById(R.id.button_1);//任务界面实例化
        Button2 = (Button) findViewById(R.id.button_2);
        Button3 = (Button) findViewById(R.id.button_3);
        Stadd = (Button) findViewById(R.id.stadd);//任务新建按钮
        calbtn = (Button) findViewById(R.id.dateChoose);//日历按钮


        spinner = (Spinner) findViewById(R.id.spinner);
        data_list = new ArrayList<String>();
        data_list.add("短期任务");
        data_list.add("长期任务");
        data_list.add("模板");
        data_list.add("档案库");
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);//下拉菜单适配器
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//安卓自带下拉菜单样式
        spinner.setAdapter(arr_adapter);//加载适配器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent intent1 = new Intent(First.this, LongtermActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        //Intent intent2 = new Intent(First.this, Filestore.class);
                        //startActivity(intent2);
                        break;
                    case 3:
                        Intent intent2 = new Intent(First.this, Filestore.class);
                        startActivity(intent2);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First.this, First.class);
                startActivity(intent);//intent 2行
            }
        });//按钮点击监听 一个参数
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First.this, Analysis.class);
//                String data = "HELLO SHIT!";
//                intent.putExtra("extra_data", data);//intent 时传字符串 2行
                startActivity(intent);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(First.this,timelog.class);
                startActivity(intent);
            }
        });


        Stadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(First.this, sec.class);
                startActivity(intent);//intent 2行
            }
        });

        dateToday = (TextView) findViewById(R.id.dateDisplay);
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
        Date curDate = new Date(System.currentTimeMillis());//获取当前系统日期
        String currDate = dateformatter.format(curDate);//将系统日期按自定义格式转换为String
        dateToday.setText(currDate);//显示出系统日期
        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);


        stTaskListView = (ListView) findViewById(R.id.sttask_listview);
//        inittask();//初始化listview
//        taskadapter adapter = new taskadapter(First.this, R.layout.task_item, taskList);
//        stTaskListView.setAdapter(adapter);
        stTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //    int position = holder.getAdapterPosition();
                ShortTask task = taskList.get(position);
                //String num = task.tasknum+"";
                Intent intent = new Intent(First.this, Stlook.class);
                intent.putExtra("extra_id",task.getid());
                //intent.putExtra("tasknum",num);
                startActivity(intent);
            }
        });

        dateToday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                taskList.clear();
                inittask();
                taskadapter adapter = new taskadapter(First.this, R.layout.task_item, taskList);
                stTaskListView.setAdapter(adapter);
            }
        });//监听日期有没有变更新listview

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        String mmDay;
        String mmMonth;
        if(mDay<10)
            mmDay = "0"+mDay;
        else
            mmDay = mDay+"";
        if(mMonth+1<10)
            mmMonth="0"+(mMonth+1);
        else
            mmMonth=(mMonth+1)+"";
        dateToday.setText(new StringBuffer().append(mYear).append("-").append(mmMonth).append("-").append(mmDay).append(""));
    }  //设置日期 利用StringBuffer追加

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };


    public void inittask() {


        String str = dateToday.getText().toString()+"  00:00";
        String str2 = dateToday.getText().toString()+"  23:59";
        taskList.clear();
        System.out.println(str);
        List<ShortTask> tasks = DataSupport.where("stasklist= ? and  starttime<= ? and endtime >= ?","1",str2,str).find(ShortTask.class);
       // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
       for(ShortTask task:tasks )
        {
   //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
    //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
           taskList.add(task);
        }
//        Cursor cursor = DataSupport.findBySQL("select * from Task where starttime <= '"+str2+"' and starttime >='"+str+"'"+" and stasklist = 1",null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int tasknum = cursor.getInt(cursor.getColumnIndex("id"));
//                String taskname = cursor.getString(cursor.getColumnIndex("name"));
//                String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
//                String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
//          //      String color = cursor.getString(cursor.getColumnIndex("taskcolor"));
//                int finish = cursor.getInt(cursor.getColumnIndex("finish"));
//                int stasklist = cursor.getInt(cursor.getColumnIndex("stasklist"));
//                int timelist = cursor.getInt(cursor.getColumnIndex("timelist"));
//                int record = cursor.getInt(cursor.getColumnIndex("record"));
//                //Task task = new Task(tasknum,taskname,starttime,endtime,color,isover,isdelete);
//                //task.init(tasknum,taskname,starttime,endtime,color,isover,isdelete);
//                taskList.add(new ShortTask(tasknum,taskname,starttime,endtime,finish,stasklist,timelist,record));
//                //Toast.makeText(First.this, starttime, Toast.LENGTH_SHORT).show();
//
//            } while (cursor.moveToNext());
//        }
//        cursor.close();





    }

}
