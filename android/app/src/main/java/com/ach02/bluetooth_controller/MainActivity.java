package com.ach02.bluetooth_controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public final MainActivity _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.controller).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(_this, ControllerActivity.class));
            }
        });

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(_this, TestControllerActivity.class));
            }
        });

        findViewById(R.id.btconfigure).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(_this, BluetoothActivity.class));
            }
        });

        findViewById(R.id.bttest).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(_this, TestBluetoothActivity.class));
            }
        });

    }
}
