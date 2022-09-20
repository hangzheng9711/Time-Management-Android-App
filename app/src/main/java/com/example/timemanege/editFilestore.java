package com.example.timemanege;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Calendar;

public class editFilestore extends Activity implements View.OnTouchListener{


    private EditText pre1;
    private EditText fin1;
    private EditText name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editfilestore);
        pre1=(EditText) findViewById(R.id.pre);
        fin1=(EditText) findViewById(R.id.fin);
        name=(EditText) findViewById(R.id.name);

        Intent intent=getIntent();
        int id = intent.getIntExtra("extra_id",-1);
        ShortTask task= DataSupport.find(ShortTask.class,id);

        name.setText(task.getName());
        pre1.setText(task.getstarttime());
        fin1.setText(task.getendtime());

        Button ok= (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                int id = intent.getIntExtra("extra_id",-1);
                ShortTask task= DataSupport.find(ShortTask.class,id);
                task.setToDefault("record");
                task.updateAll("id = ?",task.getid()+"");

                ShortTask task1= new ShortTask();

                EditText name=(EditText) findViewById(R.id.name);
                task1.setName(name.getText().toString());

                EditText pre=(EditText) findViewById(R.id.pre);
                task1.setstarttime(pre.getText().toString());

                EditText fin=(EditText) findViewById(R.id.fin);
                task1.setendtime(fin.getText().toString());

                task1.setstasklist(1);

                task1.setToDefault("record");

                task1.settimelist(1);

                task1.setToDefault("finish");

                task1.save();

                Intent intent1= new Intent(editFilestore.this,Filestore.class);
                startActivity(intent1);
            }
        });
        pre1.setOnTouchListener(this);
        fin1.setOnTouchListener(this);

//        Button queryButton = (Button) findViewById(R.id.query_data);
//        queryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<ShortTask> tasks = DataSupport.findAll(ShortTask.class);
//                for (ShortTask task: tasks) {
//                    Log.d("MainActivity", "task name is " + task.getName());
//                }
//            }
//        });
//
//        Button updateData = (Button) findViewById(R.id.update_data);
//        updateData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShortTask task = new ShortTask();
//                task.setName("sdf");
//               // task.setpyear(1005);
//                task.updateAll("name = ?", "s");
//            }
//        });
//
//        Button deleteButton = (Button) findViewById(R.id.delete_data);
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DataSupport.deleteAll(ShortTask.class, "name = ?", "sdf");
//            }
//        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Toast.makeText(editFilestore.this,"21",Toast.LENGTH_SHORT).show();
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

            if (v.getId() == R.id.pre) {
          //      Toast.makeText(editFilestore.this,"21",Toast.LENGTH_SHORT).show();
                final int inType = pre1.getInputType();
                pre1.setInputType(InputType.TYPE_NULL);
                pre1.onTouchEvent(event);
                pre1.setInputType(inType);
                pre1.setSelection(pre1.getText().length());

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

//                        sb.append("  ");
//                        sb.append(timePicker.getCurrentHour())
//                                .append(":").append(timePicker.getCurrentMinute());

                        pre1.setText(sb);
                        pre1.requestFocus();

                        dialog.cancel();
                    }
                });

            } else if (v.getId() == R.id.fin) {
                int inType = fin1.getInputType();
                fin1.setInputType(InputType.TYPE_NULL);
                fin1.onTouchEvent(event);
                fin1.setInputType(inType);
                fin1.setSelection(fin1.getText().length());

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
                        fin1.setText(sb);
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
