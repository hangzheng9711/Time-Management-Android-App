package com.example.timemanege;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class SpecificTimeActivity extends AppCompatActivity implements View.OnTouchListener{

    private EditText etStartTime;
    private EditText etEndTime;
    private Button ok;

    ArrayList<String> list1 = new ArrayList<>();
    private ListView listview;

    private EditText showandpickDate1 = null;
    private static final int DATE_DIALOG_ID1 = 1;
    private static final int SHOW_DATAPICK1 = 2;
    private int mYear1;
    private int mMonth1;
    private int mDay1;

    private EditText showandpickDate2 = null;
    private static final int DATE_DIALOG_ID2 = 3;
    private static final int SHOW_DATAPICK2= 4;
    private int mYear2;
    private int mMonth2;
    private int mDay2;


    private static final String[] m={"特定时间段","柱状分析","折线分析","时间分配"};
    private TextView view;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onResume()
    {
        super.onResume();

        String str=showandpickDate1.getText().toString()+"  "+etStartTime.getText().toString();
        String str2 =showandpickDate2.getText().toString()+"  "+etEndTime.getText().toString();
       // Toast.makeText(SpecificTimeActivity.this,str,Toast.LENGTH_SHORT).show();
        List<TimeList> times = DataSupport.where("time_start<= ? and time_end >= ?",str2,str).find(TimeList.class);
        // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
        list1.clear();
        for(TimeList time:times )
        {
                    // Toast.makeText(SpecificTimeActivity.this,"123",Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
            //     Toast.makeText(timelog.this,time.getName()+"  "+time.gettime_start()+"-"+time.gettime_end(),Toast.LENGTH_SHORT).show();
            list1.add(time.getName()+"  "+time.gettime_start()+"  -  "+time.gettime_end());
        }

        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list1));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_time_layout);

//        list1.add(0, "2017.06.03"+"     "+"吃饭");
//        list1.add(1, "2017.06.04"+"     "+"睡觉");
//        list1.add(2, "2017.06.05"+"     "+"学习");
//        list1.add(3, "2017.06.06"+"     "+"看电影");
//        list1.add(4, "2017.06.07"+"     "+"听音乐");
//        list1.add(5, "2017.06.08"+"     "+"看电影");
//        list1.add(6, "2017.06.09"+"     "+"听音乐");
//        list1.add(7, "2017.06.10"+"     "+"看电影");
//        list1.add(8, "2017.06.11"+"     "+"听音乐");
//        list1.add(9, "2017.06.12"+"     "+"听音乐");
//        list1.add(10, "2017.06.13"+"     "+"看电影");
//        list1.add(11, "2017.06.14"+"     "+"学习");
//        list1.add(12, "2017.06.15"+"     "+"睡觉");

        listview = (ListView) findViewById(R.id.listView);


        ok=(Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onResume();
            }
        });


        etStartTime = (EditText) this.findViewById(R.id.start_time);
        etEndTime = (EditText) this.findViewById(R.id.end_time);
        etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);

        showandpickDate1 = (EditText) findViewById(R.id.showDate1);
        showandpickDate1.setOnTouchListener(new SpecificTimeActivity.DateEditTextOnTouchListener1());
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        setDateTime1();

        showandpickDate2 = (EditText) findViewById(R.id.showDate2);
        showandpickDate2.setOnTouchListener(new SpecificTimeActivity.DateEditTextOnTouchListener2());
        final Calendar c2 = Calendar.getInstance();
        mYear2 = c2.get(Calendar.YEAR);
        mMonth2 = c2.get(Calendar.MONTH);
        mDay2 = c2.get(Calendar.DAY_OF_MONTH);
        setDateTime2();

        view = (TextView) findViewById(R.id.spinnerText1);
        spinner = (Spinner) findViewById(R.id.Spinner02);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        spinner.setAdapter(adapter);
        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        //设置默认值
        spinner.setVisibility(View.VISIBLE);
        Button buttonhome = (Button) findViewById(R.id.buttonhome2);
        Button buttonlog = (Button) findViewById(R.id.buttonlog2);
        buttonhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SpecificTimeActivity.this,First.class);
                startActivity(intent);
            }
        });
        buttonlog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(SpecificTimeActivity.this,timelog.class);
                startActivity(intent1);
            }
        });
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent1 = new Intent(SpecificTimeActivity.this, LongplanActivity.class);
                    startActivity(intent1);
                }
                else if(arg2==3) {
                    Intent intent2 = new Intent(SpecificTimeActivity.this, Analysis.class);
                    startActivity(intent2);
                }
                else if(arg2==2) {
                    Intent intent3 = new Intent(SpecificTimeActivity.this, specific_task_activity.class);
                    startActivity(intent3);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.only_time_picker_layout, null);
            final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker1);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.start_time) {
                etStartTime.requestFocus();
                final int inType = etStartTime.getInputType();
                etStartTime.setInputType(InputType.TYPE_NULL);
                etStartTime.onTouchEvent(event);
                etStartTime.setInputType(inType);
                etStartTime.setSelection(etStartTime.getText().length());

                builder.setTitle("选择开始时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();

                        sb.append(String.format("%02d:%02d",timePicker.getCurrentHour(), timePicker.getCurrentMinute()));

                        etStartTime.setText(sb);

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.end_time) {
                etEndTime.requestFocus();
                int inType = etEndTime.getInputType();
                etEndTime.setInputType(InputType.TYPE_NULL);
                etEndTime.onTouchEvent(event);
                etEndTime.setInputType(inType);
                etEndTime.setSelection(etEndTime.getText().length());

                builder.setTitle("选择结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%02d:%02d",timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
                        etEndTime.setText(sb);

                        dialog.cancel();
                    }
                });
            }

            Dialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3){
            //     view.setText("显示方式"+m[arg2]);
        }
        public void onNothingSelected(AdapterView<?> arg0){

        }
    }


    private void setDateTime1() {
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        updateDisplay1();
    }
    private void setDateTime2() {
        final Calendar c2 = Calendar.getInstance();
        mYear2 = c2.get(Calendar.YEAR);
        mMonth2 = c2.get(Calendar.MONTH);
        mDay2 = c2.get(Calendar.DAY_OF_MONTH);
        updateDisplay2();
    }
    /**
     * 更新日期
     */
    private void updateDisplay1() {
        showandpickDate1.setText(new StringBuilder().append(mYear1).append("-"+
                ((mMonth1 + 1) < 10 ? "0" + (mMonth1 + 1) : (mMonth1 + 1))).append("-"+(
                (mDay1 < 10) ? "0"+mDay1 : mDay1)));
    }
    private void updateDisplay2() {
        showandpickDate2.setText(new StringBuilder().append(mYear2).append("-"+
                ((mMonth2 + 1) < 10 ? "0" + (mMonth2 + 1) : (mMonth2 + 1))).append("-"+(
                (mDay2 < 10) ? "0"+mDay2 : mDay2)));
    }
    /**
     * 日期控件的事件
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear1 = year;
            mMonth1 = monthOfYear;
            mDay1 = dayOfMonth;
            updateDisplay1();
        }
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear2 = year;
            mMonth2 = monthOfYear;
            mDay2 = dayOfMonth;
            updateDisplay2();
        }
    };
    /**
     * 选择日期Button的事件处理
     *
     * @author Raul
     *
     */
    class DateEditTextOnTouchListener1 implements
            View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            showandpickDate1.requestFocusFromTouch();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Message msg = new Message();
                if (showandpickDate1.equals((EditText) v)) {
                    msg.what = SpecificTimeActivity.SHOW_DATAPICK1;
                }
                SpecificTimeActivity.this.saleHandler1.sendMessage(msg);
            }
            return true;
        }
    }
    class DateEditTextOnTouchListener2 implements
            View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            showandpickDate2.requestFocusFromTouch();
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Message msg = new Message();
                if (showandpickDate2.equals((EditText) v)) {
                    msg.what = SpecificTimeActivity.SHOW_DATAPICK2;
                }
                SpecificTimeActivity.this.saleHandler2.sendMessage(msg);
            }
            return true;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID1:
                return new DatePickerDialog(this, mDateSetListener1, mYear1, mMonth1,
                        mDay1);
            case DATE_DIALOG_ID2:
                return new DatePickerDialog(this, mDateSetListener2, mYear2, mMonth2,
                        mDay2);
        }
        return null;
    }
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case DATE_DIALOG_ID1:
                ((DatePickerDialog) dialog).updateDate(mYear1, mMonth1, mDay1);
                break;
            case DATE_DIALOG_ID2:
                ((DatePickerDialog) dialog).updateDate(mYear2, mMonth2, mDay2);
                break;
        }
    }
    /**
     * 处理日期控件的Handler
     */
    Handler saleHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SpecificTimeActivity.SHOW_DATAPICK1:
                    showDialog(DATE_DIALOG_ID1);
                    break;
            }
        }
    };
    Handler saleHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SpecificTimeActivity.SHOW_DATAPICK2:
                    showDialog(DATE_DIALOG_ID2);
                    break;
            }
        }
    };

}