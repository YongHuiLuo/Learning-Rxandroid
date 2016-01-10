package com.tiny.learning_rxandroid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class LiftExampleActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liftExample();
    }

    private void liftExample() {
        Observable.just(1, 2)
                .lift(new Observable.Operator<String, Integer>() {
                    @Override
                    public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                        return new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onCompleted();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onError(e);
                                }
                            }

                            @Override
                            public void onNext(Integer integer) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext("hello number + " + integer);
                                }
                            }
                        };
                    }
                });
    }
}
