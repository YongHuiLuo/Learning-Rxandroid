package com.tiny.learning_rxandroid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

import rx.functions.Action1;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class ThrottleFirstExampleActivity extends AppCompatActivity {

    private Button click_me;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_throttle_frist_example);
        click_me = (Button) findViewById(R.id.click_me);
        throttleFirstExample();
    }

    private void throttleFirstExample() {
        //有点 ： 防暴力点击的
        RxView.clicks(click_me)
                .throttleFirst(1000, TimeUnit.MILLISECONDS) // 设置防抖间隔为 1000ms
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(getBaseContext(), "Clicking", Toast.LENGTH_LONG).show();
                    }
                });
    }
}
