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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class specific_task_activity extends AppCompatActivity {

    private LineChart mLineChart;

    private ArrayList<String> xValues = new ArrayList<String>();
    private ArrayList<Entry> y1Values = new ArrayList<Entry>();
    private ArrayList<Entry> y2Values = new ArrayList<Entry>();

    private int i;

    private Button ok;
    private EditText taskname;
    private String name;

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


    private static final String[] m={"折线分析","柱状分析","时间分配","特定时间段"};
    private TextView view;
    private TextView view1;
    private Spinner spinner;
    private Spinner spinner1;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;

    @Override
    protected void onResume()
    {
        super.onResume();

        mLineChart = (LineChart) findViewById(R.id.linechart);
        taskname=(EditText) findViewById(R.id.taskname);

        name=taskname.getText().toString();
        String str=showandpickDate1.getText().toString()+"  00:00";
        String str2 =showandpickDate2.getText().toString()+"  23:59";
        List<TimeList> times = DataSupport.where("name=? and time_start<= ? and time_end >= ?",name,str2,str).find(TimeList.class);
        // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
        xValues.clear();
        y1Values.clear();
        y2Values.clear();
        i=0;

        for(TimeList time:times )
        {
            //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
            //     Toast.makeText(timelog.this,time.getName()+"  "+time.gettime_start()+"-"+time.gettime_end(),Toast.LENGTH_SHORT).show();
            xValues.add("第"+(i+1)+"天");
            y1Values.add(new Entry((float)time.getm_start()/(float)60, i));
            y2Values.add(new Entry((float)time.getm_end()/(float)60, i));
            i++;
        }
        LineData mLineData = getLineData(i, 100);
        showChart(mLineChart, mLineData, Color.rgb(114, 188, 223));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_task_layout);

        mLineChart = (LineChart) findViewById(R.id.linechart);
        taskname=(EditText) findViewById(R.id.taskname);

        ok=(Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onResume();
            }
        });




//      mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");



        showandpickDate1 = (EditText) findViewById(R.id.showDate1);
        showandpickDate1.setOnTouchListener(new specific_task_activity.DateEditTextOnTouchListener1());
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        setDateTime1();

        showandpickDate2 = (EditText) findViewById(R.id.showDate2);
        showandpickDate2.setOnTouchListener(new specific_task_activity.DateEditTextOnTouchListener2());
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
                Intent intent = new Intent(specific_task_activity.this,First.class);
                startActivity(intent);
            }
        });
        buttonlog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(specific_task_activity.this,timelog.class);
                startActivity(intent1);
            }
        });

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==2) {
                    Intent intent1 = new Intent(specific_task_activity.this, Analysis.class);
                    startActivity(intent1);
                }
                else if(arg2==1)
                {
                    Intent intent2 = new Intent(specific_task_activity.this, LongplanActivity.class);
                    startActivity(intent2);
                }
                else if(arg2==3)
                {
                    Intent intent3 = new Intent(specific_task_activity.this, SpecificTimeActivity.class);
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
                    msg.what = specific_task_activity.SHOW_DATAPICK1;
                }
                specific_task_activity.this.saleHandler1.sendMessage(msg);
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
                    msg.what = specific_task_activity.SHOW_DATAPICK2;
                }
                specific_task_activity.this.saleHandler2.sendMessage(msg);
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
                case specific_task_activity.SHOW_DATAPICK1:
                    showDialog(DATE_DIALOG_ID1);
                    break;
            }
        }
    };
    Handler saleHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case specific_task_activity.SHOW_DATAPICK2:
                    showDialog(DATE_DIALOG_ID2);
                    break;
            }
        }
    };

    // 设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//

        lineChart.setBackgroundColor(Color.WHITE);// 设置背景

        // add data
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色
//      mLegend.setTypeface(mTf);// 字体

        lineChart.animateX(2500); // 立即执行的动画,x轴
    }

    /**
     * 生成一个数据
     * @param count 表示图表中有多少个坐标点
     * @param range 用来生成range以内的随机数
     * @return
     */
    private LineData getLineData(int count, float range) {


        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataSet1 = new LineDataSet(y1Values, "开始"+name /*显示在比例图上*/);
        // mLineDataSet.setFillAlpha(110);
        // mLineDataSet.setFillColor(Color.RED);

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataSet2 = new LineDataSet(y2Values, "结束"+name /*显示在比例图上*/);
        // mLineDataSet.setFillAlpha(110);
        // mLineDataSet.setFillColor(Color.RED);

        //用y轴的集合来设置参数
        lineDataSet2.setLineWidth(1.75f); // 线宽
        lineDataSet2.setCircleSize(3f);// 显示的圆形大小
        lineDataSet2.setColor(Color.rgb(65,178,114));// 显示颜色
        lineDataSet2.setCircleColor(Color.rgb(65,178,114));// 圆形的颜色
        lineDataSet2.setHighLightColor(Color.rgb(65,178,114)); // 高亮的线的颜色
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawCubic(false);
        lineDataSet2.setDrawFilled(true);
        lineDataSet2.setFillColor(Color.rgb(239,255,239));

        lineDataSet1.setLineWidth(1.75f); // 线宽
        lineDataSet1.setCircleSize(3f);// 显示的圆形大小
        lineDataSet1.setColor(Color.rgb(242,163,101));// 显示颜色
        lineDataSet1.setCircleColor(Color.rgb(242,163,101));// 圆形的颜色
        lineDataSet1.setHighLightColor(Color.rgb(242,163,101)); // 高亮的线的颜色
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setDrawCubic(false);
        lineDataSet1.setDrawFilled(true);
        lineDataSet1.setFillColor(Color.rgb(254,227,198));

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet1); // add the datasets
        lineDataSets.add(lineDataSet2); // add the datasets


        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }
}