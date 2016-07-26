package com.tiny.learning_rxandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tiny.model.Track;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created JackLuo
 * 实现主要功能：
 * 创建时间： on 2016/1/20.
 * 修改者： 修改日期： 修改内容：
 */
public class LyricExampleActivity extends AppCompatActivity {
    private static final String TAG = LyricExampleActivity.class.getSimpleName();

    private Track track;

    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(20);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "Fans Task #" + mCount.getAndIncrement());
            // th.setPriority(Thread.);
            // return th;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_example);
        init();
    }

    public static ThreadPoolExecutor createDefaultThreadPool() {

        return new ThreadPoolExecutor(5, Integer.MAX_VALUE, 1, TimeUnit.SECONDS, sWorkQueue,
                sThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

    private void init() {
        Observable.just(track)
                .observeOn(Schedulers.from(createDefaultThreadPool()))
                .map(new Func1<Track, String>() {
                    @Override
                    public String call(Track track) {
                        return getDrcUrl(track);
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<String, byte[]>() {
                    @Override
                    public byte[] call(String s) {
                        return getLyric(s);
                    }
                })
                .observeOn(Schedulers.newThread())
                .map(new Func1<byte[], byte[]>() {
                    @Override
                    public byte[] call(byte[] bytes) {
                        return downloadLyric(bytes.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<byte[]>() {
                    @Override
                    public void call(byte[] bytes) {
                        setLrc(bytes.toString());
                    }
                });

    }

    private String getDrcUrl(final Track track) {
        //TODO 网络请求，获取动态歌词的URL
        String url = "";
        return url;
    }

    public byte[] getLyric(final String url) {
        return readFile(url);
    }

    private byte[] readFile(final String url) {
        return new byte[0];
    }

    public byte[] downloadLyric(final String lyricurl) {
        return new byte[0];
    }

    //页面更新URL
    public void setLrc(String text) {

    }
}
