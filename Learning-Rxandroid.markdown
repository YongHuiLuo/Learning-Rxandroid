# Learning-Rxandroid
---
Because interest RxJava, RxAndroid, RxBinding of creating this project. RxAndroid making program coding for asynchronous operations more simple, in addition, the observer pattern design for my project and enhance the ability of encoding are very helpful. I compiled in the process of learning a few examples related to common methods, the future will continue to be updated. In addition, creating About RxAndroid.PPT documents on the network based on learning materials and personal understanding. Hope can help to want to know and learn with friends RxJava and RxAndroid projects. On commonly used methods, there are several examples:

（译）因为对RxJava、RxAndroid、RxBinding 的兴趣，创建此项目。RxAndroid使得程序中对异步操作的编码更简洁，另外，观察者模式的设计理念对我的项目和编码的能力的提升都很有帮助。我在学习的过程中整理了一些相关常用方法的例子，以后仍然会继续更新。此外，根据网络上的学习资料和个人的理解创建<i class="icon-file"></i>About RxAndroid.PPT的文档。希望能够帮助到想了解和学习RxJava和RxAndroid项目的朋友。关于常用的方法，有如下几个例子：

>* RxBaseExampleActivity 
>* UsageExampleActivity
>* SchedulerExampleActivity
>* MapExampleActivity
>* FlatMapExampleActivity
>* ThrottleFirstExampleActivity
>* LiftExampleActivity
>* SchedulerMultiExampleActivity
>* ComposeExampleActivity

If you want to see the PPT, there is a file named About RxJava.pptx documentation, introduction of RxJava and RxAndroid some understanding in the project.。

（译）如果你想查看PPT，在项目中有文件名为 <i class="icon-file"></i>About RxJava.pptx的文档,介绍对RxJava和RxAndroid的一些理解。

Project-related class introduce（项目相关类介绍）
---

###RxBaseExampleActivity
Create Observable, Subscribe objects in two different ways, onSubscribe achieve subscriptions between the observer and the observed, ActionX use.

（译）创建Observable、Subscriber对象的两种不同方式，onSubscribe实现观察者和被观察者之间的订阅，ActionX的使用。

###UsageExampleActivity
Use from and create to create Observable, traversal collection without using a For loop, reading the resource file and show.

（译）使用方法from和create来创建Observable，不使用For循环实现集合的遍历，读取资源文件并展示。

###SchedulerExampleActivity
By using subscribeOn or ObserveOn method to achieve scheduling between threads, a simple line of code that is realized on the main UI thread operations.

（译）通过使用subscribeOn 或者 ObserveOn 方法 实现线程之间的调度，简单的一行代码即实现UI操作在主线程上进行。

``` java
.subscribeOn(Schedulers.io())
.observeOn(AndroidSchedulers.mainThread())
```

###MapExampleActivity
Use map to achieve transformation 1 to 1 which is a major feature RxJava - the transformation. map can transform one object into another object, and finally passed to the observer. String example by according to the type of the file name is converted into Bitmap with ImageView on display illustrate this feature. There are other examples, supplementary illustrate this point.

（译）使用方法map实现1对1的变换这是RxJava的一大特点 -- 变换。map能将一个对象变换成另外的一个对象，并最终传递给观察者。例子中通过根据String类型的文件名转换成Bitmap并用ImageView进行展示说明这个特点。还有其他的例子，补充说明这一点。

###FlatMapExampleActivity
Methods flatMap, is another way to achieve transformation. Not accurate to say that he achieved one to more transformation, for example, a student can have multiple output course, this method return value is an Observable object about his interpretation are described in ppt document.

（译）方法flatMap，是实现变换的另外一个方法。不准确的说他实现了one to more的变换，比如一个学生可以有多门课程的输出，此方法返回值是一个Observable对象，关于他的解释在ppt文档中有说明。

###ThrottleFirstExampleActivity
This is an example RxBinding project, to achieve the prevention of violence demand View is clicked, this is a small demand point I personally prefer the following code:

（译）这是RxBinding项目的一个例子，实现了防止View被暴力点击的需求，这是我个人比较喜欢的一个小需求点，代码如下：
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
RxBinding View operations in many of the convenience and the methods used, I will continue to update, if you are interested, you can also help to perfect her, to help more people understand and use RxBinding.

（译）RxBinding在对View的操作上还有很多便捷和使用的方法，我也会持续更新，如果你有兴趣，也可以帮助来完善她，方便更多的人了解和使用RxBinding。

###LiftExampleActivity
Lift the method code to achieve the principles of transformation, including map and flatmap eventually call to lift to achieve. On the principle of lift are described in ppt document.

（译）在方法Lift的代码中实现了变换的原理，包括map和flatmap最终都会调用到lift来实现。关于lift的原理在ppt文档中有介绍。

###SchedulerMultiExampleActivity
An example of complex use, including transformation and thread scheduling, involving many transformations and multiple thread switches. The thread ID input display after each change in TextView, the ease of understanding. Here involves using Schedulers class can create the following several common scheduler:

（译）复合使用的一个例子，包括变换和线程调度，涉及到多次变换和多次线程切换。将每次变换之后的线程ID在TextView中输入展示，方便理解。此处涉及到Schedulers类的使用，能够创建如下几个常用的调度器：

>>* Schedulers.io()
>>* Schedulers.newThread()
>>* Schedulers.computation()
>>* Schedulers.immediate()
>>* Schedulers.trampoline()
>>* AndroidSchedulers.mainThread()

###ComposeExampleActivity
Methods compose help achieve when there are multiple lift, simplify the code.

（译）方法compose帮助实现当有多个lift，简化代码。

Related open source projects（相关的开源项目）
---
https://github.com/ReactiveX/RxAndroid
https://github.com/JakeWharton/RxBinding

Blog reference and learning materials（参考Blog及学习资料）
---

 - http://gank.io/post/560e15be2dca930e00da1083 《给 Android 开发者的 RxJava详解》 对我学习Rx系列项目受益非常大的博文，包括PPT的整理相关的内容来自这篇博文，在此感谢作者 -- 抛物线。
 - http://blog.csdn.net/lzyzsd/article/details/41833541 深入浅出RxJava（一：基础篇）
 - http://blog.csdn.net/lzyzsd/article/details/44094895 深入浅出RxJava ( 二：操作符 )
 - http://blog.csdn.net/lzyzsd/article/details/44891933 深入浅出RxJava三--响应式的好处
 - http://blog.csdn.net/lzyzsd/article/details/45033611 深入浅出RxJava四-在Android中使用响应式编程
 - 项目中的源码
 - 及其他相关方面的Blog