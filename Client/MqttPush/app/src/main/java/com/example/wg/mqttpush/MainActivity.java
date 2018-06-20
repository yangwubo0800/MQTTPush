package com.example.wg.mqttpush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = findViewById(R.id.mqtt_connect);
        bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                MqttService.startMqtt(MainActivity.this);
            }
        });
    }
}
