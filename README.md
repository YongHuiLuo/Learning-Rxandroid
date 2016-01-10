# Learning-Rxandroid
---

我对Rx系列开源项目很感谢兴趣，包括RxJava、RxAngndroid、RxBinding等，他的实用性和观察者模式的设计理念对我的项目和编码的能力的提升都很有帮助。我在学习的过程中整理了一些相关的常用方法的例子，创建了本项目，而且以后也会继续更新。此外，根据网络上的学习资料和个人的理解，创建了RxJava简介PPT，希望对想理解RxJava和RxAndroid的朋友有所帮助。关于常用的方法，有如下几个例子：

>* RxBaseExampleActivity 
>* UsageExampleActivity
>* SchedulerExampleActivity
>* MapExampleActivity
>* FlatMapExampleActivity
>* ThrottleFirstExampleActivity
>* LiftExampleActivity
>* SchedulerMultiExampleActivity
>* ComposeExampleActivity

如果你想查看PPT，在项目有一个文件，名字叫 <i class="icon-file"></i>About RxJava.pptx,介绍对RxAndroid的一些理解。

项目相关类介绍
---

###RxBaseExampleActivity
创建Observable和Subscriber的不同方式，onSubscribe实现观察者和被观察者直接的订阅以及ActionX的使用。

###UsageExampleActivity
使用方法from和create来创建Observable，不使用For循环实现集合的遍历，读取资源文件并展示。

###SchedulerExampleActivity
实现线程调度的方法subscribeOn和ObserveOn的简单使用，一行代码实现UI操作在主线程进行。

``` java
.subscribeOn(Schedulers.io())
.observeOn(AndroidSchedulers.mainThread())
```

###MapExampleActivity
使用方法map实现1对1的变换，这也是RxJava的一大特点--变换，能将一个对象变换成另外的一个对象，并最终传递给观察者。例子中实现了根据String类型的文件名转换成Bitmap并用ImageView进行展示，还有另外一个例子，补充说明这一点。

###FlatMapExampleActivity
方法flatMap，是实现变换的另外一个方法，不准确的说他实现了一对多的变换，比如一个学生对多门课程的输出等问题，此方法返回值是一个Observable对象，关于他的解释在ppt文档中有说明。

###ThrottleFirstExampleActivity
这是RxBinding项目的一个例子，实现了防止View被暴力点击的需求，这是我个人比较喜欢的一个小需求点，代码如下：
``` java
  RxView.clicks(click_me)
                .throttleFirst(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        Toast.makeText(getBaseContext(), "Clicking", Toast.LENGTH_LONG).show();
                    }
                });
```
RxBinding在对View的操作上还有很多便捷和使用的方法，我也会持续更新，如果你有兴趣，也可以帮助来完善她，方便更多的人了解和使用RxBinding。

###LiftExampleActivity
方法Lift是变换的原理代码，包括map和flatmap最终都会调用到lift来实现，关于lift的原理在ppt文档中有介绍。

###SchedulerMultiExampleActivity
复合使用的一个例子，包括变换和线程调度，设计到多次变换和多次线程调度，例子中将每次变换之后的线程ID在TextView中输入展示，方便大家理解。此处涉及到Schedulers类的使用，能够创建如下几个常用的调度器：

>>* Schedulers.io()
>>* Schedulers.newThread()
>>* Schedulers.computation()
>>* Schedulers.immediate()
>>* Schedulers.trampoline()
>>* AndroidSchedulers.mainThread()

###ComposeExampleActivity
方法compose帮助实现当有多个lift（即多次变换的时


相关的开源项目
---
https://github.com/ReactiveX/RxAndroid
https://github.com/JakeWharton/RxBinding

参考Blog及学习资料
---

 - http://gank.io/post/560e15be2dca930e00da1083 《给 Android 开发者的 RxJava详解》 对我学习Rx系列项目受益非常大的博文，包括PPT的整理相关的内容来自这篇博文，在此感谢作者 -- 抛物线。
 - http://blog.csdn.net/lzyzsd/article/details/41833541 深入浅出RxJava（一：基础篇）
 - http://blog.csdn.net/lzyzsd/article/details/44094895 深入浅出RxJava ( 二：操作符 )
 - http://blog.csdn.net/lzyzsd/article/details/44891933 深入浅出RxJava三--响应式的好处
 - http://blog.csdn.net/lzyzsd/article/details/45033611 深入浅出RxJava四-在Android中使用响应式编程
 - 项目中的源码
 - 及其他相关方面的Blog