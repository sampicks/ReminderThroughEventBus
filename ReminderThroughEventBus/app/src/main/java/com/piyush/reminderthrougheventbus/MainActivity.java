package com.piyush.reminderthrougheventbus;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private EditText edt_Time;
    private Button btn_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //register EventBus (this activity is a subscriber)
        EventBus.getDefault().register(this);
        edt_Time= (EditText) findViewById(R.id.editText);
        btn_Save= (Button) findViewById(R.id.button);

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(!TextUtils.isEmpty(edt_Time.getText().toString()) && TextUtils.isDigitsOnly(edt_Time.getText().toString()))
             {
                    int sec=Integer.parseInt(edt_Time.getText().toString());
                 if(sec>0 && sec<60)
                 {
                     final Handler handler = new Handler();
                     handler.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             //Do something after 100ms

                             EventBus.getDefault().post("Call Method");

                         }
                     }, sec*1000);
                 }
                 else
                     Toast.makeText(MainActivity.this,"Please input time in 1-59 seconds",Toast.LENGTH_SHORT).show();
             }
                else
                 Toast.makeText(MainActivity.this,"Please provide input time in seconds",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregister eventbus here
        EventBus.getDefault().unregister(this);
    }
    public void onEvent(String event){

        //check if login was successful
            startActivity(new Intent(this, TimeUpActivity.class));
    }
}
