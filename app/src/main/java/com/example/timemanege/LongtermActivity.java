package com.example.timemanege;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class LongtermActivity extends ListActivity {
    private List<LongTask> mData = new ArrayList<>();
    Button calbtn;//日历按钮
    int mYear, mMonth, mDay;
    TextView dateToday;
    final int DATE_DIALOG = 1;



    private SQLiteDatabase db;

    public LongtermActivity(){ db= Connector.getDatabase();}//连接数据库

    @Override
    protected void onResume(){
        super.onResume();
        mData.clear();
        inittask();
        LongtermAdapter adapter = new LongtermAdapter(LongtermActivity.this, R.layout.longterm_item, mData);
       setListAdapter(adapter);
    }


    public void inittask() {


        String str = dateToday.getText().toString() + "  00:00";
        String str2 = dateToday.getText().toString() + "  23:59";
        mData.clear();
        System.out.println(str);
        List<LongTask> tasks = DataSupport.where("ltasklist= ? and  starttime<= ? and endtime >= ?", "1", str2, str).find(LongTask.class);
        // List<ShortTask> tasks1 = DataSupport.where("starttime<= ?",str2).where("stasklist= ?","0").where("endtime>= ?",str).find(ShortTask.class);
        for (LongTask task : tasks) {
            //         Toast.makeText(First.this,task.getstarttime(),Toast.LENGTH_SHORT).show();
            //       Toast.makeText(First.this,task.getendtime(),Toast.LENGTH_SHORT).show();
            mData.add(task);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longterm);


        Button button1=(Button)findViewById(R.id.button_back_main);//为返回设置点击事件
        Button button2=(Button)findViewById(R.id.button_new);

        calbtn = (Button) findViewById(R.id.dateChoose);//日历按钮
        dateToday = (TextView) findViewById(R.id.dateDisplay);

        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");//定义日期格式
        Date curDate = new Date(System.currentTimeMillis());//获取当前系统日期
        String currDate = dateformatter.format(curDate);//将系统日期按自定义格式转换为String
        dateToday.setText(currDate);//显示出系统日期
        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v){
                Intent intent=new  Intent(LongtermActivity.this,First.class);
                startActivity(intent);
            }

        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v){
                Intent intent=new  Intent(LongtermActivity.this,newActivity.class);
                startActivity(intent);
            }

        });
        dateToday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                mData.clear();
                inittask();
                LongtermAdapter adapter = new LongtermAdapter(LongtermActivity.this, R.layout.longterm_item, mData);
                setListAdapter(adapter);
            }
        });//监听日期有没有变更新listview
    }

    //listview中某项选中后的逻辑
    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Log.v("MyListView4-click", (String)mData.get(position).get("textView"));
    }
*/
    /**
     * listview中点击按键弹出对话框
     */
    public void showInfo(){
        Intent intent=new Intent(LongtermActivity.this,playActivity.class);
        startActivity(intent);


    }


    public class LongtermAdapter extends ArrayAdapter<LongTask> {//适配器
        private int resourceId;
        public LongtermAdapter(Context context, int textViewResourceId, List<LongTask>objects){
            super(context,textViewResourceId,objects);
            resourceId=textViewResourceId;
        }

       // private LayoutInflater mInflater;
     //   public LongtermAdapter(Context context){this.mInflater = LayoutInflater.from(context);
       // }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

    /*    @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
*/
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final LongTask task=getItem(position);
            View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            TextView taskname=(TextView)view.findViewById(R.id.longterm);
            //TextView taskdate=(TextView)view.findViewById(R.id.task_date);
            taskname.setText(task.getName());
            Button btn_edit=(Button)view.findViewById(R.id.view_btn);
            btn_edit.setOnClickListener(new View.OnClickListener(){
                @Override
                public  void onClick(View v){
                    Intent intent=new Intent(LongtermActivity.this,playActivity.class);
                    intent.putExtra("extra_id",task.getid());
                    startActivity(intent);
                }

            });
            return view;
        }

    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        String mmDay;
        String mmMonth;
        if(mDay<10)
            mmDay = "0"+mDay;
        else
            mmDay = mDay+"";
        if(mMonth+1<10)
            mmMonth="0"+(mMonth+1);
        else
            mmMonth=(mMonth+1)+"";
        dateToday.setText(new StringBuffer().append(mYear).append("-").append(mmMonth).append("-").append(mmDay).append(""));
    }  //设置日期 利用StringBuffer追加

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
}
