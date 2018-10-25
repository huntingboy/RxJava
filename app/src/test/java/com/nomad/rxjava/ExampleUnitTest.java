package com.nomad.rxjava;

import com.nomad.domain.Developer;
import com.nomad.domain.Student;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void helloWorldSimple() {
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        };

        Observable.just("hello world", "second", "third").subscribe(consumer);
    }

    @Test
    public void helloWorldComplex() {
        Observer<String> observer = new Observer<String>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
                if (s.equals("!")) {
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        Observable.just("hello", "!").subscribe(observer);
    }

    @Test
    public void helloWorldPlus() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("send hello world");
                e.onNext("hello world");
                System.out.println("send !");
                e.onNext("!");
                System.out.println("send !");
                e.onNext("!");
                System.out.println("send complete");
                e.onComplete();
            }
        });

        observable.subscribe(observer);
    }

    @Test
    public void filter() {
        Observable.just("包子", "馒头", "肠粉", "春卷", "饺子", "炒粉")
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        System.out.println("test: " + s);
                        return s.equals("饺子");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("accept:" + s);
                    }
                });
    }

    @Test
    public void map() {
        Student student = new Student("huntingboy");
        Observable.just(student).map(new Function<Student, Developer>() {
            @Override
            public Developer apply(Student student) throws Exception {
                System.out.println("apply: " + student.toString());
                Developer developer = new Developer(student.getName());
                developer.setSkill("Android");
                return developer;
            }
        }).subscribe(new Observer<Developer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Developer developer) {
                System.out.println("onNext: " + developer.toString());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }


}