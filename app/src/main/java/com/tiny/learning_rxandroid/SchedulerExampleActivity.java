package com.tiny.learning_rxandroid;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class SchedulerExampleActivity extends AppCompatActivity {
    private static final String TAG = SchedulerExampleActivity.class.getSimpleName();
    private ImageView img_rx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler_example);
        img_rx = (ImageView) findViewById(R.id.img_rx);
        schedulerExample();
    }

    private void schedulerExample() {
        img_rx = (ImageView) findViewById(R.id.img_rx);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.drawable.rx_android_example_1);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        img_rx.setImageDrawable(drawable);
                        Log.d(TAG, "onNext");
                    }
                });
    }
}
