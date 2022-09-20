package com.example.timemanege;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StaskAdapter extends RecyclerView.Adapter<StaskAdapter.ViewHolder>{

    private List<ShortTask> mStaskList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View staskView;
        TextView staskName;
        TextView staskId;
        Button staskEdit;
        Button staskDelete;

        public ViewHolder(View view) {
            super(view);
            staskView = view;
            staskName = (TextView) view.findViewById(R.id.stask_name);
            staskEdit = (Button) view.findViewById(R.id.stask_edit);
            staskDelete = (Button) view.findViewById(R.id.stask_delete);
        }
    }

    public StaskAdapter(List<ShortTask> staskList) {
        mStaskList = staskList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stask_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.staskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ShortTask shorttask = mStaskList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + shorttask.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.staskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ShortTask shorttask = mStaskList.get(position);
                Toast.makeText(v.getContext(), "you clicked view " + shorttask.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShortTask shorttask = mStaskList.get(position);
        holder.staskName.setText(shorttask.getName());
        holder.staskId.setText(shorttask.getid());
    }

    @Override
    public int getItemCount() {
        return mStaskList.size();
    }

}