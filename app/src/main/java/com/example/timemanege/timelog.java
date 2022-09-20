package com.example.timemanege;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class timelog extends AppCompatActivity {

    private ListView listview;
    ArrayList<String> list1 = new ArrayList<>();



    private EditText showandpickDate = null;
    private static final int DATE_DIALOG_ID = 1;
    private static final int SHOW_DATAPICK = 0;
    private int mYear;
    private int mMonth;
    private int mDay;



    @Override
    protected void onResume()
    {
        super.onResume();
        String str=showandpickDate.getText().toString()+"  00:00";
        String str2 =showandpickDate.getText().toString()+"  23:59";
        List<TimeList> times = DataSupport.where("time_start<= ? and time_end >= ?",str2,str).find(TimeList.class);
        // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
        list1.clear();
        for(TimeList time:times )
        {
            //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
       //     Toast.makeText(timelog.this,time.getName()+"  "+time.gettime_start()+"-"+time.gettime_end(),Toast.LENGTH_SHORT).show();
            list1.add(time.getName()+"  "+time.gettime_start()+"  ~  "+time.gettime_end());
        }

        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list1));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timelog_layout);

        listview = (ListView) findViewById(R.id.listView);

//        list1.add(0, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(1, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(2, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(3, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(4, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(5, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(6, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(7, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(8, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(9, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(10, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(11, "吃饭"+"     "+"22:00"+"-"+"23:00");
//        list1.add(12, "吃饭"+"     "+"22:00"+"-"+"23:00");


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 String str2=list1.get(position);
                //Toast.makeText(timelog.this,str2,Toast.LENGTH_SHORT).show();
                 char str22[]=str2.toCharArray();
                 StringBuffer str2_name=new StringBuffer();
                StringBuffer str2_start=new StringBuffer();
                StringBuffer str2_end=new StringBuffer();
                 int j=0;
                for(int i=0;i<str2.length();i++)
                {
                    if(j==0&&str22[i]!=' ')
                    {
                        str2_name.append(str22[i]);
                    }
                    else if(j==0&&str22[i]==' ')
                    {
                        j++;
                        continue;
                    }
                    else if(j==1&&str22[i]!='~')
                    {
                        str2_start.append(str22[i]);
                    }
                    else if(j==1&&str22[i]=='~')
                    {
                        j++;
                        continue;
                    }
                    else
                    {
                        str2_end.append(str22[i]);
                    }

                }
                String str2name=str2_name.toString();
                String str2start=str2_start.toString();
                String str2end=str2_end.toString();
//                Toast.makeText(timelog.this,str2name,Toast.LENGTH_SHORT).show();
//                Toast.makeText(timelog.this,str2start,Toast.LENGTH_SHORT).show();
//                Toast.makeText(timelog.this,str2end,Toast.LENGTH_SHORT).show();

                String str1=showandpickDate.getText().toString();

                Intent intent = new Intent(timelog.this, look_activity.class);
                intent.putExtra("extra_str1",str1);
                intent.putExtra("extra_str2name",str2name);
                intent.putExtra("extra_str2start",str2start);
                intent.putExtra("extra_str2end",str2end);


                startActivity(intent);
            }
        });

        ImageButton buttonadd =(ImageButton) findViewById(R.id.buttonadd);
        buttonadd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(timelog.this,AddeditActivity.class);
                startActivity(intent);
            }
        });



        showandpickDate = (EditText) findViewById(R.id.showDate);
        showandpickDate.setOnTouchListener(new DateEditTextOnTouchListener());
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        setDateTime();




        Button buttonhome = (Button) findViewById(R.id.buttonhome1);
        Button buttonfenxi =(Button) findViewById(R.id.buttonfenxi1);
        buttonhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(timelog.this,First.class);
                startActivity(intent);
            }
        });
        buttonfenxi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(timelog.this,Analysis.class);
                startActivity(intent1);
            }
        });


    }



    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
    }
    /**
     * 更新日期
     */
    private void updateDisplay() {
        showandpickDate.setText(new StringBuilder().append(mYear).append("-"+
                ((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))).append("-"+(
                (mDay < 10) ? "0"+mDay : mDay)));
    }
    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
            onResume();
    //        Toast.makeText(timelog.this,"123",Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 选择日期Button的事件处理
     *
     * @author Raul
     *
     */
    class DateEditTextOnTouchListener implements
            View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            showandpickDate.requestFocusFromTouch();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Message msg = new Message();
                if (showandpickDate.equals((EditText) v)) {
                    msg.what = timelog.SHOW_DATAPICK;
                }
                timelog.this.saleHandler.sendMessage(msg);
            }
            return true;
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }
    /**
     * 处理日期控件的Handler
     */
    Handler saleHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case timelog.SHOW_DATAPICK:
                    showDialog(DATE_DIALOG_ID);
                    break;
            }
        }
    };



}
