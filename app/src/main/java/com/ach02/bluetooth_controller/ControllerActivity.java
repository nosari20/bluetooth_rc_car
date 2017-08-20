package com.ach02.bluetooth_controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ach02.bluetooth_controller.controllers.joysticks.Joystick;



public class ControllerActivity extends AppCompatActivity {


    private Joystick lr_joystick, fb_joystick;
    private Button light, sound;

    private TextView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        lr_joystick = (Joystick) findViewById(R.id.lr_joystick);
        fb_joystick = (Joystick) findViewById(R.id.fb_joystick);

        light = (Button) findViewById(R.id.light);
        sound = (Button) findViewById(R.id.sound);

        log = (TextView) findViewById(R.id.log) ;


        initListener();

    }

    private void initListener(){

        Joystick.IsJoystickChanged fb_isJoystickChanged = new Joystick.IsJoystickChanged() {
            @Override
            public boolean compare(double old_angle, int old_power, Joystick.Direction old_direction, double new_angle, int new_power, Joystick.Direction new_direction) {
                if(old_direction == Joystick.Direction.LEFT || old_direction == Joystick.Direction.RIGHT) return true;
                if(Math.abs(old_angle) <= 90 &&  Math.abs(new_angle) > 90) return true;

                if(Math.abs(old_angle) >= 90 &&  Math.abs(new_angle) < 90) return true;

                if(old_power >= 50 &&  new_power < 50) return true;
                if(old_power <= 50 &&  new_power > 50) return true;

                return false;
            }
        };

        fb_joystick.setIsJoystickChanged(fb_isJoystickChanged);

        fb_joystick.setOnJoystickMoveListener(new Joystick.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(double angle, int power, Joystick.Direction direction) {
                if(direction == Joystick.Direction.NONE){
                    send("stop");
                }else{
                    if(power > 50){
                        if(
                            Math.abs(angle) <= 90
                        ){
                            send("forward_fast");
                        }else{
                            send("backward_fast");
                        }
                    }else{
                        if(
                            Math.abs(angle) <= 90
                        ){
                            send("forward_slow");
                        }else{
                            send("backward_slow");
                        }
                    }

                }

            }
        }, Joystick.DEFAULT_LOOP_INTERVAL);

        Joystick.IsJoystickChanged lr_isJoystickChanged = new Joystick.IsJoystickChanged() {
            @Override
            public boolean compare(double old_angle, int old_power, Joystick.Direction old_direction, double new_angle, int new_power, Joystick.Direction new_direction) {

                if(old_direction == Joystick.Direction.FRONT || old_direction == Joystick.Direction.BOTTOM) return true;
                if(old_angle <= 0 &&  new_angle > 0) return true;
                if(old_angle >= 0 &&  new_angle < 0) return true;

                if(old_power >= 50 &&  new_power < 50) return true;
                if(old_power <= 50 &&  new_power > 50) return true;

                return false;
            }
        };

        lr_joystick.setIsJoystickChanged(lr_isJoystickChanged);

        lr_joystick.setOnJoystickMoveListener(new Joystick.OnJoystickMoveListener() {
            @Override
            public void onValueChanged(double angle, int power, Joystick.Direction direction) {
                if(direction == Joystick.Direction.NONE){
                    send("middle");
                }else{
                    if(power > 50){
                        if(angle > 0){
                            send("right_2");
                        }else{
                            send("left_2");
                        }
                    }else{
                        if(angle > 0){
                            send("right_1");
                        }else{
                            send("left_1");
                        }
                    }
                }

            }
        }, Joystick.DEFAULT_LOOP_INTERVAL);

        light.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                send("light");
            }
        });

        sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                send("sound");
            }
        });
    }

    private void send(String command){
        log.setText(command);
    }
}
