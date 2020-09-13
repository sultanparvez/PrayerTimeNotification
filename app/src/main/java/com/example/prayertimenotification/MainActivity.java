package com.example.prayertimenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bt;
    Button bt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt=findViewById(R.id.btn_time);
        bt2=findViewById(R.id.btn_map);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,dailytime.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"Loading Time",Toast.LENGTH_SHORT).show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,maplocator.class);
                startActivity(i);
                finish();
                Toast.makeText(getApplicationContext(),"Loading Map",Toast.LENGTH_SHORT).show();
            }
        });

    }
}