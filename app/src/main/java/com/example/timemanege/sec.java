package com.example.timemanege;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class sec extends Activity implements View.OnTouchListener {

    private EditText etStartTime;
    private EditText etEndTime;
    private EditText etTaskName;
    private RadioGroup colorSelectGroup;
    private Button taskAddButton;
   // private PreferencesUtils preferencesUtils;
    String color;
    //private MyDB dbHelper;
    //private Button cha;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_2);

       // dbHelper = new MyDB(this,"Task.db",null,1);

    //    preferencesUtils = null;
        color = "红";

        taskAddButton = (Button) this.findViewById(R.id.task_add_button);
        etStartTime = (EditText) this.findViewById(R.id.et_start_time);
        etEndTime = (EditText) this.findViewById(R.id.et_end_time);
        etTaskName = (EditText) this.findViewById(R.id.task_add_name);
        colorSelectGroup = (RadioGroup)this.findViewById(R.id.colorselect);
        colorSelectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) sec.this.findViewById(radioButtonId);
                color = rb.getText().toString();
            }
        });
        taskAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShortTask task1=new ShortTask();


             task1.setName(etTaskName.getText().toString());

               task1.setstarttime(etStartTime.getText().toString());

               task1.setendtime(etEndTime.getText().toString());

                task1.setstasklist(1);

                task1.setrecord(0);

                task1.settimelist(1);

                task1.setfinish(0);

                task1.saveFast();


                Intent intent = new Intent(sec.this, First.class);
                Toast.makeText(sec.this,"你成功新建了一项任务～",Toast.LENGTH_SHORT).show();
                startActivity(intent);

               // int tasknum = preferencesUtils.getInt(getBaseContext(),"tasknum");
               // preferencesUtils.putInt(getBaseContext(),"tasknum",tasknum+1);

               // String taskname = etTaskName.getText().toString();

               // String taskcolor = "red";
               // switch (color){
//                    case "红":
//                        taskcolor = "red";
//                        break;
//                    case "黄":
//                        taskcolor = "yellow";
//                        break;
//                    case "蓝":
//                        taskcolor = "blue";
//                        break;
//                    case "绿":
//                        taskcolor = "green";
//                        break;
//                    case "紫":
//                        taskcolor = "purple";
//                        break;
//                    case "粉":
//                        taskcolor = "pink";
//                        break;
//                }
               // boolean isover = false;
              //  boolean isdelete = false;
                //Task.init(tasknum,taskname,starttime,endtime,taskcolor);
               // SQLiteDatabase db = dbHelper.getReadableDatabase();
               // dbHelper.dbadd(db,tasknum,taskname,starttime,endtime,taskcolor,0,0);
            }
        });

       etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
         //Toast.makeText(sec.this,"21",Toast.LENGTH_SHORT).show();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = View.inflate(this, R.layout.date_time_dialog, null);
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
            final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
            builder.setView(view);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);

            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(Calendar.MINUTE);

            if (v.getId() == R.id.et_start_time) {
                final int inType = etStartTime.getInputType();
                etStartTime.setInputType(InputType.TYPE_NULL);
                etStartTime.onTouchEvent(event);
                etStartTime.setInputType(inType);
                etStartTime.setSelection(etStartTime.getText().length());

                builder.setTitle("选取起始时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d  %02d:%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute()));


                        etStartTime.setText(sb);
                        etEndTime.requestFocus();

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.et_end_time) {
                int inType = etEndTime.getInputType();
                etEndTime.setInputType(InputType.TYPE_NULL);
                etEndTime.onTouchEvent(event);
                etEndTime.setInputType(inType);
                etEndTime.setSelection(etEndTime.getText().length());

                builder.setTitle("选取结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d  %02d:%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute()));
//                        sb.append("  ");
//                        sb.append(timePicker.getCurrentHour())
//                                .append(":").append(timePicker.getCurrentMinute());
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
}

