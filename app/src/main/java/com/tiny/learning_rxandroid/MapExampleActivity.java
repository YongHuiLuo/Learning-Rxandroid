package com.tiny.learning_rxandroid;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.tiny.model.Student;
import com.tiny.tools.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by ${Tiny} on 2016/1/8.
 */
public class MapExampleActivity extends AppCompatActivity {
    private static final String TAG = MapExampleActivity.class.getSimpleName();
    private ImageView img_rx_2;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_map_example);
        img_rx_2 = (ImageView) findViewById(R.id.img_rx_2);
        mapExample();
    }

    private void mapExample() {
        //例子1

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
            Log.d(TAG, "name -- >" + students.get(i));
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
                        Log.d(TAG, "name -- >" + s);
                    }
                });
    }
}
