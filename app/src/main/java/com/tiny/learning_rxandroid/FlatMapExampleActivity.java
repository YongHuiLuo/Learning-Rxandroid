package com.tiny.learning_rxandroid;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tiny.model.Student;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class FlatMapExampleActivity extends AppCompatActivity {
    private static final String TAG = FlatMapExampleActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flatMapExample();
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
        int size = students.size();
        for (int i = 0; i < size; i++) {
            int courseSize = students.get(i).cources.size();
            for (int j = 0; j < courseSize; j++) {
                Log.d(TAG, "student name -->" + students.get(i).name + " ;courses -- >" + students.get(i).cources.get(j));
            }
        }

        //2、 Rx写法
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<String>>() {
                    @Override
                    public Observable<String> call(Student student) {
                        Log.d(TAG, "call execute num");
                        return Observable.from(student.cources);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, "Rx course name --" + s);
                    }
                });

        //3、 issue 打印出学生名称和对应的课程名称,用Flatmap很难实现。

        Observable.from(students)
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        for (int i = 0; i < student.cources.size(); i++) {
                            Log.d(TAG, "name -- " + student.name + ";course --" + student.cources.get(i));
                        }
                    }
                });
    }

}
