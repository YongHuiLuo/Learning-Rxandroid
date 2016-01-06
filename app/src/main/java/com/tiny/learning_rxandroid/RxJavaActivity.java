package com.tiny.learning_rxandroid;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.tiny.model.Student;
import com.tiny.tools.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${Tiny} on 2015/12/25.
 */
public class RxJavaActivity extends AppCompatActivity {

    private final static String TAG = "RxJava";
    private ImageView img_rx;
    private Button click_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_android_layout);
        findView();

        //rxBaseExample();
        //usageExample();
        //schedulerExample();

        //变换
        //mapExample();
        //flatMapExample();

        //去抖动
        //throttleFirstExample();

        //liftExample();

        composeExample();
    }

    private void findView() {
        click_me = (Button) findViewById(R.id.click_me);
    }

    private void rxBaseExample() {
        /**
         * 0 ： Schedulers
         */
//        Observable.just("One","two","three","four","five")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Log.i(TAG,"s --" + s);
//                    }
//                });


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
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>(){
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                Log.d(TAG,"Observable.create - ");
//                subscriber.onNext("One!");
//                subscriber.onNext("Two!");
//                subscriber.onNext("Three!");
//                subscriber.onNext("Four!");
//                subscriber.onCompleted();
//            }
//        });

        /**
         *  2 ： 定义一个被观察者，使用just
         */
//        Observable<String> observable = Observable.just("One!","Two!","Three!","Four!");


        /**
         * 2 ： 定义一个被观察者，使用from
         */
        //String[] numbs = {"One!", "Two!", "Three"};
        //Observable<String> observable = Observable.from(numbs);

        /**
         * 3 ； 两种订阅方式，这里可以看出：事件的发送是发生在被订阅的时候
         */
        //observable.subscribe(observer);
        //observable.subscribe(subscriber);

        /**
         * 3 ： 不完成定义回调的订阅方式
         */
//        Action1<String> onNextAction = new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.d(TAG, "onNextAction s--" + s);
//            }
//        };
//
//        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
//            @Override
//            public void call(Throwable throwable) {
//                Log.d(TAG, "onErrorAction throwable --" + throwable);
//            }
//        };
//
//        Action0 onCompleteAction = new Action0() {
//            @Override
//            public void call() {
//                Log.d(TAG, "onCompleteAction");
//            }
//        };
//
//        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
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

    private void schedulerExample() {
        img_rx = (ImageView) findViewById(R.id.img_rx);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(R.drawable.rx_android_example_1);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
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

    private void mapExample() {
        //例子1

        final ImageView img_rx_2 = (ImageView) findViewById(R.id.img_rx_2);
        Observable.just("test.jpg")
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String fileName) {
                        return BitmapUtil.getBitmapFromPath(fileName);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        img_rx_2.setImageBitmap(bitmap);
                    }
                });


        //例子2

        List<String> courses = new ArrayList<>();
        courses.add("数学");
        courses.add("语文");

        List<Student> students = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            students.add(new Student("member + " + i, courses));
        }

        //TODO 获取每个学生的名称
        //1、 原始写法
        int size = students.size();
        for (int i = 0; i < size; i++) {
            Log.d("Tiny", "name -- >" + students.get(i));
        }

        //2、 Rx写法
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.name;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("Tiny", "name -- >" + s);
                    }
                });
    }

    private void flatMapExample() {

        List<String> courses = new ArrayList<>();
        courses.add("数学");
        courses.add("语文");

        final List<Student> students = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            students.add(new Student("member + " + i, courses));
        }

        //TODO 获取每个学生的名称
        //1、 原始写法
//        int size = students.size();
//        for (int i = 0; i < size; i++) {
//            int courseSize = students.get(i).cources.size();
//            for (int j = 0; j < courseSize; j++) {
//                Log.d("Tiny", "student name -->" + students.get(i).name + " ;courses -- >" + students.get(i).cources.get(j));
//            }
//        }

        //2、 Rx写法
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        Log.d("Tiny", "call execute num");
                        return Observable.from(student.cources);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d("Tiny", "Rx course name --" + s);
                    }
                });

        //3、 issue 打印出学生名称和对应的课程名称,用Flatmap很难实现。

//        Observable.from(students)
//                .subscribe(new Action1<Student>() {
//                    @Override
//                    public void call(Student student) {
//                        for (int i = 0; i < student.cources.size(); i++) {
//                            Log.d("Tiny", "name -- " + student.name + ";course --" + student.cources.get(i));
//                        }
//                    }
//                });
    }

    private void throttleFirstExample() {
//        RxView.clickEvents(click_me)
//                .throttleFirst(1000, TimeUnit.MILLISECONDS) // 设置防抖间隔为 1000ms
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        Toast.makeText(getApplicationContext(),"Click Me",Toast.LENGTH_SHORT);
//                    }
//                });
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

    private void composeExample() {
//        Observable.just(1)
//                .lift(operator1)
//                .lift(operator2)
//                .lift(operator3)
//                .lift(operator4)
//                .subscribe(subscriber1);
//
//        Observable.just(2)
//                .lift(operator1)
//                .lift(operator2)
//                .lift(operator3)
//                .lift(operator4)
//                .subscribe(subscriber2);
//
//        Observable.just(3)
//                .lift(operator1)
//                .lift(operator2)
//                .lift(operator3)
//                .lift(operator4)
//                .subscribe(subscriber3);
//
//        Observable.just(4)
//                .lift(operator1)
//                .lift(operator2)
//                .lift(operator3)
//                .lift(operator4)
//                .subscribe(subscriber1);

        //1 、 第一次优化改动

//        liftAll(Observable.just(1)).subscribe(subscriber1);
//        liftAll(Observable.just(2)).subscribe(subscriber2);
//        liftAll(Observable.just(3)).subscribe(subscriber3);
//        liftAll(Observable.just(4)).subscribe(subscriber1);

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
