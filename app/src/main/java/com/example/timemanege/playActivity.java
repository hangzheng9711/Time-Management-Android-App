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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class playActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {
    private Spinner spinner;
    private EditText etStartTime;
   private EditText etEndTime;
    private EditText prename;
    private EditText prestrdate;
    private EditText preenddate;
    private EditText prefrequence;
    private Button button4;
    int id;
    private Button button5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ActionBar actionbar = getSupportActionBar();//隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }

        Button button1 = (Button) findViewById(R.id.button_back);
        Button button2 = (Button) findViewById(R.id.button_edit);
        Button button3 = (Button) findViewById(R.id.button_delete);
        Button button6 = (Button) findViewById(R.id.color);
         button4 = (Button) findViewById(R.id.start_date);
         button5 = (Button) findViewById(R.id.end_date);
        etStartTime = (EditText) this.findViewById(R.id.textView_startdate);
        etEndTime = (EditText) this.findViewById(R.id.textView_enddate);
        spinner = (Spinner) findViewById(R.id.spiner_frequence);
        prename=(EditText)findViewById(R.id.in_name) ;
        prestrdate=(EditText)findViewById(R.id.textView_startdate) ;
        preenddate=(EditText)findViewById(R.id.textView_enddate) ;
        prefrequence=(EditText)findViewById(R.id.edittext_frequence) ;

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnTouchListener(this);
        button5.setOnTouchListener(this);
        button6.setOnClickListener(this);
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

        Intent intent=getIntent();
        id = intent.getIntExtra("extra_id",-1);
       LongTask task= DataSupport.find(LongTask.class,id);
       prename.setText(task.getName());
       prestrdate.setText(task.getstarttime());
        preenddate.setText(task.getendtime());
        prefrequence.setText(task.gettimes()+"");
        String a=task.getperiod();
      //  Toast.makeText(playActivity.this,a,Toast.LENGTH_SHORT).show();
        if(a.equals("每天"))
        {
            spinner.setSelection(0,true);
        }
         else if(a.equals("每周"))
        {
            spinner.setSelection(1,true);
        }
        else if(a.equals("每月"))
        {
            spinner.setSelection(2,true);
        }
        else
        {
            spinner.setSelection(3,true);
        }


    }


        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button_back:
                        final Intent intent = new Intent(playActivity.this, LongtermActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.button_edit:
                        Intent intent1=new Intent(playActivity.this,editActivity.class);
                        intent1.putExtra("extra_id",id);
                        startActivity(intent1);
                        break;
                    case R.id.button_delete:
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(playActivity.this);
                        alertDialog.setTitle("是否删除？");
                        alertDialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }

                        });
                        alertDialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                Intent intent2 = new Intent(playActivity.this, LongtermActivity.class);
                                startActivity(intent2);
                            }
                        });
                        alertDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                                LongTask task=new LongTask();
                                task=DataSupport.find(LongTask.class,id);
                                task.setToDefault("ltasklist");
                                task.update(id);
                                Toast.makeText(playActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                Intent intent4=new Intent(playActivity.this,LongtermActivity.class);
                                startActivity(intent4);
                            }
                        });
                        alertDialog.show();
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



