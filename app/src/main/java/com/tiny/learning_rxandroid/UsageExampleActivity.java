package com.tiny.learning_rxandroid;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class UsageExampleActivity extends AppCompatActivity {
    private static final String TAG = UsageExampleActivity.class.getSimpleName();
    private ImageView img_rx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_example);
        img_rx = (ImageView) findViewById(R.id.img_rx);
        usageExample();
    }

    private void usageExample() {

        /**
         * 打印字符串数组
         */
        String[] contents = new String[0];
        ArrayList<String> mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mData.add("item " + i);
        }
        Observable.from(mData.toArray(contents))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        Log.d(TAG, name);
                    }
                });

        /**
         * 2、由资源ID取得图片并展示
         */
        img_rx = (ImageView) findViewById(R.id.img_rx);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.drawable.rx_android_example_1);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
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

        //这里方法里的例子，并没有说明RxAndroid有什么过人之处，反而增加了，没有必要的代码。
    }
}
