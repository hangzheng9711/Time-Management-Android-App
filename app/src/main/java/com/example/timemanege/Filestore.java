package com.example.timemanege;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class Filestore extends AppCompatActivity {

    private SQLiteDatabase db;

    public Filestore(){ db=Connector.getDatabase();}

    @Override
    protected void onResume(){
        super.onResume();
        List<ShortTask> tasks = DataSupport.where("record= ?","1").find(ShortTask.class);
        staskList.clear();
        for (ShortTask task: tasks) {
            staskList.add(task);
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        StaskAdapter adapter = new StaskAdapter(staskList);
        recyclerView.setAdapter(adapter);

    }



    public class StaskAdapter extends RecyclerView.Adapter<com.example.timemanege.StaskAdapter.ViewHolder>{



        private List<ShortTask> mStaskList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            View staskView;
            TextView staskName;
            Button  staskEdit;
            Button staskDelete;
        //    TextView staskId;

            public ViewHolder(View view) {
                super(view);
                staskView = view;
                staskName = (TextView) view.findViewById(R.id.stask_name);
                staskEdit = (Button) view.findViewById(R.id.stask_edit);
                staskDelete = (Button) view.findViewById(R.id.stask_delete);
        //        staskId= (TextView) view.findViewById(R.id.stask_id);
            }
        }


        public StaskAdapter(List<ShortTask> staskList) {
            mStaskList = staskList;
        }

        @Override
        public com.example.timemanege.StaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stask_item, parent, false);
            final com.example.timemanege.StaskAdapter.ViewHolder holder = new com.example.timemanege.StaskAdapter.ViewHolder(view);

            holder.staskDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.stask_delete:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Filestore.this);
                        dialog.setMessage("Delete?");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,int which){
                                int position = holder.getAdapterPosition();
                                ShortTask shorttask = mStaskList.get(position);
                                shorttask.setToDefault("record");
//                                ShortTask shorttask1= new ShortTask();
//                                shorttask1.setToDefault("record");
                                shorttask.update(shorttask.getid());
                                onResume();
                            }
                        });
                        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,int which){

                            }
                        });
                        dialog.show();
                        break;
                        default:
                            break;
                    }

                }
            });

            holder.staskEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    ShortTask shorttask = mStaskList.get(position);
                    Intent intent= new Intent(Filestore.this,editFilestore.class);
                    intent.putExtra("extra_id",shorttask.getid());
                    startActivity(intent);
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(com.example.timemanege.StaskAdapter.ViewHolder holder, int position) {
            ShortTask shorttask = mStaskList.get(position);
            holder.staskName.setText(shorttask.getName());
       //     holder.staskId.setText(shorttask.getid()+"");
        }

        @Override
        public int getItemCount() {
            return mStaskList.size();
        }

    }

    private List<ShortTask> staskList = new ArrayList<ShortTask>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filestore);

        Button Button1 = (Button) findViewById(R.id.button_1);//任务界面实例化
        Button Button2 = (Button) findViewById(R.id.button_2);
        Button Button3 = (Button) findViewById(R.id.button_3);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filestore.this, First.class);
                startActivity(intent);//intent 2行
            }
        });//按钮点击监听 一个参数
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Filestore.this, Analysis.class);
//                String data = "HELLO SHIT!";
//                intent.putExtra("extra_data", data);//intent 时传字符串 2行
                startActivity(intent);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Filestore.this,timelog.class);
                startActivity(intent);
            }
        });

//        Button addData = (Button) findViewById(R.id.add_shorttask);
//        addData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShortTask stask = new ShortTask();
//                stask.settimelist(1);
//                stask.setName("s");
//                stask.setrecord(1);
//                stask.save();
//            }
//        });


    }

}
