package com.tiny.learning_rxandroid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class ComposeExampleActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        composeExample();
    }

    private void composeExample() {
        Observable.just(1)
                .lift(operator1)
                .lift(operator2)
                .lift(operator3)
                .lift(operator4)
                .subscribe(subscriber1);

        Observable.just(2)
                .lift(operator1)
                .lift(operator2)
                .lift(operator3)
                .lift(operator4)
                .subscribe(subscriber2);

        Observable.just(3)
                .lift(operator1)
                .lift(operator2)
                .lift(operator3)
                .lift(operator4)
                .subscribe(subscriber3);

        Observable.just(4)
                .lift(operator1)
                .lift(operator2)
                .lift(operator3)
                .lift(operator4)
                .subscribe(subscriber1);

        //1 、 第一次优化改动

        liftAll(Observable.just(1)).subscribe(subscriber1);
        liftAll(Observable.just(2)).subscribe(subscriber2);
        liftAll(Observable.just(3)).subscribe(subscriber3);
        liftAll(Observable.just(4)).subscribe(subscriber1);

        //2、 第二次优化 Observable 可以利用传入的 Transformer 对象的 call 方法直接对自身进行处理，也就不必被包在方法的里面了。
        MyTransformer transformer = new MyTransformer();
        Observable.just(1).compose(transformer).subscribe(subscriber1);
        Observable.just(2).compose(transformer).subscribe(subscriber2);
        Observable.just(3).compose(transformer).subscribe(subscriber3);
        Observable.just(4).compose(transformer).subscribe(subscriber1);
    }

    private Observable liftAll(Observable observable) {
        return observable
                .lift(operator1)
                .lift(operator2)
                .lift(operator3)
                .lift(operator4);
    }

    Subscriber<Integer> subscriber1 = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Integer integer) {
            Log.d("Tiny", "compose 1 --" + integer);
        }
    };

    Subscriber<Integer> subscriber2 = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Integer integer) {
            Log.d("Tiny", "compose 2 --" + integer);
        }
    };

    Subscriber<Integer> subscriber3 = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Integer integer) {
            Log.d("Tiny", "compose 3 --" + integer);
        }
    };

    Observable.Operator operator1 = new Observable.Operator<String, Integer>() {
        @Override
        public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
            return new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Integer integer) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext("1" + integer);
                    }
                }
            };
        }
    };

    Observable.Operator operator2 = new Observable.Operator<Integer, String>() {
        @Override
        public Subscriber<? super String> call(final Subscriber<? super Integer> subscriber) {
            return new Subscriber<String>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(String string) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(Integer.parseInt(string));
                    }
                }
            };
        }
    };

    Observable.Operator operator3 = new Observable.Operator<String, Integer>() {
        @Override
        public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
            return new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Integer integer) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext("3" + integer);
                    }
                }
            };
        }
    };

    Observable.Operator operator4 = new Observable.Operator<Integer, String>() {
        @Override
        public Subscriber<? super String> call(final Subscriber<? super Integer> subscriber) {
            return new Subscriber<String>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(String string) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(Integer.parseInt(string));
                    }
                }
            };
        }
    };

    public class MyTransformer implements Observable.Transformer<Integer,Integer>{
        @Override
        public Observable<Integer> call(Observable<Integer> observable) {
            return observable
                    .lift(operator1)
                    .lift(operator2)
                    .lift(operator3)
                    .lift(operator4);
        }
    }
}
