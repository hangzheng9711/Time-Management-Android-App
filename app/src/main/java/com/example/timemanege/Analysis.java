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
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class Analysis extends AppCompatActivity {

    private PieChart mChart;

    private long sum;
    private int i;
    private int color_value1=15;
    private int color_value2=142;
    private int color_value3=232;


    private Button ok;
    ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
    ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
    ArrayList<Integer> colors = new ArrayList<Integer>();

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


    private static final String[] m={"时间分配","柱状分析","折线分析","特定时间段"};
    private TextView view;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onResume()
    {
        super.onResume();
        mChart = (PieChart) findViewById(R.id.bingPieChart);
        String str=showandpickDate1.getText().toString()+"  00:00";
        String str2 =showandpickDate2.getText().toString()+"  23:59";
        List<TimeList> times = DataSupport.where("time_start<= ? and time_end >= ?",str2,str).find(TimeList.class);
        // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
        xValues.clear();
        yValues.clear();
        sum=0;
        i=0;

        for(TimeList time:times )
        {
            //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
            //     Toast.makeText(timelog.this,time.getName()+"  "+time.gettime_start()+"-"+time.gettime_end(),Toast.LENGTH_SHORT).show();
            sum=sum+time.getm_end()-time.getm_start();
        }
        for(TimeList time:times )
        {
            //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
            //     Toast.makeText(timelog.this,time.getName()+"  "+time.gettime_start()+"-"+time.gettime_end(),Toast.LENGTH_SHORT).show();
            xValues.add(time.getName());
            yValues.add(new Entry((float)(time.getm_end()-time.getm_start())/(float)sum, i));
            colors.add(Color.rgb(color_value1, color_value2, color_value3));
            color_value1=(color_value1+45)%215;
            color_value2=(color_value2+56)%215;
            color_value3=(color_value3+121)%215;
            i++;
        }

        PieData mPieData = getPieData(i, 100);
        showChart(mChart, mPieData);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.analysis_layout);

        //mChart = (PieChart) findViewById(R.id.bingPieChart);

        ok=(Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onResume();
            }
        });

        showandpickDate1 = (EditText) findViewById(R.id.showDate1);
        showandpickDate1.setOnTouchListener(new Analysis.DateEditTextOnTouchListener1());
        final Calendar c1 = Calendar.getInstance();
        mYear1 = c1.get(Calendar.YEAR);
        mMonth1 = c1.get(Calendar.MONTH);
        mDay1 = c1.get(Calendar.DAY_OF_MONTH);
        setDateTime1();

        showandpickDate2 = (EditText) findViewById(R.id.showDate2);
        showandpickDate2.setOnTouchListener(new Analysis.DateEditTextOnTouchListener2());
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
                Intent intent = new Intent(Analysis.this,First.class);
                startActivity(intent);
            }
        });
        buttonlog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(Analysis.this,timelog.class);
                startActivity(intent1);
            }
        });
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(arg2==1) {
                    Intent intent1 = new Intent(Analysis.this, LongplanActivity.class);
                    startActivity(intent1);
                }
               else if(arg2==3) {
                    Intent intent2 = new Intent(Analysis.this, SpecificTimeActivity.class);
                    startActivity(intent2);
                }
                else if(arg2==2) {
                    Intent intent3 = new Intent(Analysis.this, specific_task_activity.class);
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
                    msg.what = Analysis.SHOW_DATAPICK1;
                }
                Analysis.this.saleHandler1.sendMessage(msg);
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
                    msg.what = Analysis.SHOW_DATAPICK2;
                }
                Analysis.this.saleHandler2.sendMessage(msg);
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
                case Analysis.SHOW_DATAPICK1:
                    showDialog(DATE_DIALOG_ID1);
                    break;
            }
        }
    };
    Handler saleHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Analysis.SHOW_DATAPICK2:
                    showDialog(DATE_DIALOG_ID2);
                    break;
            }
        }
    };


    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(70f);  //半径
        pieChart.setTransparentCircleRadius(0f); // 半透明圈
        pieChart.setHoleRadius(0);  //实心圆

        pieChart.setDescription(" ");

        // mChart.setDrawYValues(true);
        pieChart.setDrawSliceText(false);
        pieChart.setDrawCenterText(false);  //饼状图中间不可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

 //       pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(LegendPosition.BELOW_CHART_CENTER);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(10f);
        mLegend.setYEntrySpace(2f);
        mLegend.setWordWrapEnabled(true);
        mLegend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {


        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, " "/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置饼状图之间的距离
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setValueFormatter(new PercentFormatter());

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}