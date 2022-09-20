package com.example.timemanege;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class look_activity extends AppCompatActivity {

    private EditText date=null;
    private EditText start=null;
    private EditText end=null;
    private EditText name=null;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_layout);

        date=(EditText) findViewById(R.id.date2);
        start=(EditText) findViewById(R.id.start2);
        end=(EditText) findViewById(R.id.end2);
        name=(EditText) findViewById(R.id.name2);

        Intent intent=getIntent();
        date.setText(intent.getStringExtra("extra_str1"));
        name.setText(intent.getStringExtra("extra_str2name"));
        start.setText(intent.getStringExtra("extra_str2start"));
        end.setText(intent.getStringExtra("extra_str2end"));

        back=(Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(look_activity.this,timelog.class);
                startActivity(intent);
            }
        });

    }
}
