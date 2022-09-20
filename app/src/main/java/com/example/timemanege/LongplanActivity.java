package com.example.timemanege;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class LongplanActivity extends AppCompatActivity {

    private String name;
    private EditText taskname;
    private BarChart mBarChart;
    private BarData mBarData;

    private Button ok;

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
    private ArrayList<String> xValues = new ArrayList<String>();
    private ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();


    private static final String[] m={"柱状分析","时间分配","折线分析","特定时间段"};

    private TextView view;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    private TextView view1;
    private Spinner spinner1;
    private ArrayAdapter<String> adapter1;

    @Override
    protected void onResume()
    {
        super.onResume();

        mBarChart = (BarChart) findViewById(R.id.Spread_Bar_Chart);
        taskname=(EditText) findViewById(R.id.taskname);

        name=taskname.getText().toString();
        String str=showandpickDate1.getText().toString()+"  00:00";
        String str2 =showandpickDate2.getText().toString()+"  23:59";
        List<TimeList> times = DataSupport.where("name=? and time_start<= ? and time_end >= ?",name,str2,str).find(TimeList.class);
        // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
        xValues.clear();
        yValues.clear();

        int i=0;
        for(TimeList time:times )
        {
            //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
            //     Toast.makeText(timelog.this,time.getName()+"  "+time.gettime_start()+"-"+time.gettime_end(),Toast.LENGTH_SHORT).show();
            xValues.add("第"+(i+1)+"天");
            yValues.add(new BarEntry(time.getm_end()-time.getm_start(),i));
            i++;
        }
        mBarData = getBarData(i, 100);
        showBarChart(mBarChart, mBarData);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.longplan_layout);



        showandpickDate1 = (EditText) findViewById(R.id.showDate1);;
            showandpickDate1.setOnTouchListener(new LongplanActivity.DateEditTextOnTouchListener1());
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        setDateTime1();

        showandpickDate2 = (EditText) findViewById(R.id.showDate2);
        showandpickDate2.setOnTouchListener(new LongplanActivity.DateEditTextOnTouchListener2());
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

        ok=(Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onResume();
            }
        });

        Button buttonhome = (Button) findViewById(R.id.buttonhome2);
        Button buttonlog = (Button) findViewById(R.id.buttonlog2);
        buttonhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LongplanActivity.this,First.class);
                startActivity(intent);
            }
        });
        buttonlog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(LongplanActivity.this,timelog.class);
                startActivity(intent1);
            }
        });
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent1 = new Intent(LongplanActivity.this, Analysis.class);
                    startActivity(intent1);
                }
                else if(arg2==3) {
                    Intent intent2 = new Intent(LongplanActivity.this, SpecificTimeActivity.class);
                    startActivity(intent2);
                }
                else if(arg2==2) {
                    Intent intent3 = new Intent(LongplanActivity.this, specific_task_activity.class);
                    startActivity(intent3);
                }
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }}
        );

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
                    msg.what = LongplanActivity.SHOW_DATAPICK1;
                }
                LongplanActivity.this.saleHandler1.sendMessage(msg);
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
                    msg.what = LongplanActivity.SHOW_DATAPICK2;
                }
                LongplanActivity.this.saleHandler2.sendMessage(msg);
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
                case LongplanActivity.SHOW_DATAPICK1:
                    showDialog(DATE_DIALOG_ID1);
                    break;
            }
        }
    };
    Handler saleHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LongplanActivity.SHOW_DATAPICK2:
                    showDialog(DATE_DIALOG_ID2);
                    break;
            }
        }
    };

    private void showBarChart(BarChart barChart, BarData barData) {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框

        barChart.setDescription("");// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");

        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸

        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        barChart.setPinchZoom(false);//

//      barChart.setBackgroundColor();// 设置背景

        barChart.setDrawBarShadow(true);

        barChart.setData(barData); // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示

        mLegend.setForm(LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

//      X轴设定
     XAxis xAxis = barChart.getXAxis();
      xAxis.setPosition(XAxisPosition.BOTTOM);

        barChart.animateX(2500); // 立即执行的动画,x轴
    }

    private BarData getBarData(int count, float range) {


        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "长期任务统计图");

        barDataSet.setColor(Color.rgb(114, 188, 223));

        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSets);

        return barData;
    }
}