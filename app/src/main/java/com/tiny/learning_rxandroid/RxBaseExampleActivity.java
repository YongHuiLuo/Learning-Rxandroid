package com.tiny.learning_rxandroid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class RxBaseExampleActivity extends AppCompatActivity {
    private static final String TAG = RxBaseExampleActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rxBaseExample();
    }

    private void rxBaseExample() {
        /**
         * 0 ： Schedulers
         */
        Observable.just("One", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG,"s --" + s);
                    }
                });

        /**
         * 1 ： 定义一个观察者Observer
         */
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext s - " + s);
            }
        };

        /**
         * 1 ： 定义一个观察者Subscriber
         */
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "subscriber onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "subscriber onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "subscriber onNext s - " + s);
            }
        };

        /**
         * 2 ： 定义一个被观察者，使用create（）方式
         */
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG,"Observable.create - ");
                subscriber.onNext("One!");
                subscriber.onNext("Two!");
                subscriber.onNext("Three!");
                subscriber.onNext("Four!");
                subscriber.onCompleted();
            }
        });

        /**
         *  2 ： 定义一个被观察者，使用just
         */
        Observable<String> observable2 = Observable.just("One!","Two!","Three!","Four!");

        /**
         * 2 ： 定义一个被观察者，使用from
         */
        String[] numbs = {"One!", "Two!", "Three"};
        Observable<String> observable3 = Observable.from(numbs);

        /**
         * 3 ； 两种订阅方式，这里可以看出：事件的发送是发生在被订阅的时候
         */
        observable.subscribe(observer);
        observable.subscribe(subscriber);

        /**
         * 3 ： 不完成定义回调的订阅方式
         */
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "onNextAction s--" + s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "onErrorAction throwable --" + throwable);
            }
        };

        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                Log.d(TAG, "onCompleteAction");
            }
        };

        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
    }
}
