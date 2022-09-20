package com.example.timemanege;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class newActivity extends AppCompatActivity implements View.OnTouchListener,View.OnClickListener {
    private Spinner spinner;
    private EditText etStartTime;
    private EditText etEndTime;
    String period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        final ActionBar actionbar = getSupportActionBar();//隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }
        Button button1 = (Button) findViewById(R.id.button_back);
        Button button2 = (Button) findViewById(R.id.button_check);
        Button button3 = (Button) findViewById(R.id.color);
        Button button4 = (Button) findViewById(R.id.start_date);
        Button button5 = (Button) findViewById(R.id.end_date);


        etStartTime = (EditText) this.findViewById(R.id.textView_startdate);
        etEndTime = (EditText) this.findViewById(R.id.textView_enddate);
        spinner = (Spinner) findViewById(R.id.spiner_frequence);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnTouchListener(this);
        button5.setOnTouchListener(this);
        etStartTime.setOnTouchListener(this);
        etEndTime.setOnTouchListener(this);


        List<String> data_list = new ArrayList<String>();
        data_list.add("每天");
        data_list.add("每周");
        data_list.add("每月");
        data_list.add("每年");
        //适配器
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        period="每天";
                        break;
                    case 1:
                        //Intent intent2 = new Intent(First.this, Filestore.class);
                        //startActivity(intent2);
                        period="每周";
                        break;
                    case 2:
                        period="每月";
                        break;
                    case 3:
                        period="每年";
                        break;
                    default:
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void onClick(View v) {
        final EditText name = (EditText) findViewById(R.id.in_name);
        final EditText times = (EditText) findViewById(R.id.edittext_frequence);

        switch (v.getId()) {

            case R.id.button_back:
                Intent intent1 = new Intent(newActivity.this, LongtermActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_check://点击保存后
                AlertDialog.Builder dialog = new AlertDialog.Builder(newActivity.this);
                dialog.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
                dialog.setTitle("是否保存？");
                dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//点击取消留在当前界面
                        dialog.dismiss(); //关闭dialog
                    }
                });
                dialog.setPositiveButton("否", new DialogInterface.OnClickListener() {//点击否时退回长期规划界面
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(newActivity.this, LongtermActivity.class);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("是", new DialogInterface.OnClickListener() {//点击是 将内容保存
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        LongTask task1 = new LongTask();

                        //           EditText name=(EditText) findViewById(R.id.task_add_name);
                        task1.setName(name.getText().toString());

                        //            String starttime = etStartTime.getText().toString();
                        //          String endtime = etEndTime.getText().toString();

                        //   EditText stime=(EditText) findViewById(R.id.et_start_time);
                        task1.setstarttime(etStartTime.getText().toString());

                        //        EditText etime=(EditText) findViewById(R.id.et_end_time);
                        task1.setendtime(etEndTime.getText().toString());

                        task1.setltasklist(1);
                        String a = times.getText().toString();
                        int b = Integer.valueOf(a);
                        task1.settimes(b);
                        task1.settimelist(1);
                     //   task1.setperiod(String.valueOf(period));
                        task1.setperiod(period);
                        task1.saveFast();
                        Intent intent = new Intent(newActivity.this, LongtermActivity.class);
                        startActivity(intent);

                        Toast.makeText(newActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        //`````````````

                    }
                });
                dialog.show();
                break;
            case R.id.color://点击选择颜色按钮
                final ColorPickerDialog dialog1;
                final TextView textView = (TextView) findViewById(R.id.textView_color);
                Context context;
                context = this;
                dialog1 = new ColorPickerDialog(context, textView.getTextColors().getDefaultColor(),
                        getResources().getString(R.string.app_name),
                        new ColorPickerDialog.OnColorChangedListener() {
                            @Override
                            public void colorChanged(int color) {
                                textView.setBackgroundColor(color);
                            }
                        });
                dialog1.show();
                break;
            default:
                break;
        }

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

            if (v.getId() == R.id.start_date) {
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
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        sb.append("  ");
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());

                        etStartTime.setText(sb);
                        etEndTime.requestFocus();

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.end_date) {
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







