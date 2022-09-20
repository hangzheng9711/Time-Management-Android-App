package com.example.timemanege;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/27 0027.
 */

public class taskadapter extends ArrayAdapter<ShortTask> {
    private int resourceId;
    public taskadapter(Context context, int textViewResourceId, List<ShortTask>objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ShortTask task=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.isover_image);
        TextView taskname=(TextView)view.findViewById(R.id.task_name);
        //TextView taskdate=(TextView)view.findViewById(R.id.task_date);
        taskname.setText(task.getName());
        if(task.getfinish()==1){
            imageView.setImageResource(R.drawable.done);
        }
        else{
           // imageView.setImageResource(R.drawable.undone);
        }
        return view;
    }
}
