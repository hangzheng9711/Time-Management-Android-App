package com.example.timemanege;

import android.content.Intent;
import android.os.SystemClock;
import android.widget.Button;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.litepal.crud.DataSupport;

public class AddeditActivity extends AppCompatActivity implements View.OnTouchListener{
    private EditText etStartTime;
    private EditText etEndTime;
    private EditText name;
    private Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedit_layout);

        etStartTime = (EditText) this.findViewById(R.id.et_start_time);
        etEndTime = (EditText) this.findViewById(R.id.et_end_time);
        name=(EditText)this.findViewById(R.id.task_add_name);
        ok=(Button)this.findViewById(R.id.task_add_button );

        etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShortTask task= new ShortTask();
                task.setName(name.getText().toString());
                task.setstarttime(etStartTime.getText().toString());
                task.setendtime(etEndTime.getText().toString());
                task.setstasklist(0);
                task.setrecord(0);
                task.settimelist(1);
                task.setfinish(0);
                task.save();
                TimeList time=new TimeList();
                time.setName(name.getText().toString());
                time.settime_start(etStartTime.getText().toString());
                time.settime_end(etEndTime.getText().toString());
                char str11[]=etStartTime.getText().toString().toCharArray();
                long a=((str11[12]-'0')*10+(str11[13]-'0'))*60+(str11[15]-'0')*10+(str11[16]-'0');
                char str12[]=etEndTime.getText().toString().toCharArray();
                long b=((str12[12]-'0')*10+(str12[13]-'0'))*60+(str12[15]-'0')*10+(str12[16]-'0');
                time.setm_start(a);
                time.setm_end(b);
                time.setid(task.getid());
                time.setToDefault("s_or_l");
                time.save();
                Intent intent1= new Intent(AddeditActivity.this,timelog.class);
                startActivity(intent1);
            }
        });
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
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
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());

                        etStartTime.setText(sb);
                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.et_end_time) {
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
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());
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
