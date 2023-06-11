package atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 描述：     演示AtomicIntegerFieldUpdater的用法
 */
public class AtomicIntegerFieldUpdaterDemo implements Runnable{

    static Student tom;
    static Student peter;

    public static AtomicIntegerFieldUpdater<Student> scoreUpdater = AtomicIntegerFieldUpdater
            .newUpdater(Student.class, "myId");

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            peter.myId++;
            scoreUpdater.getAndIncrement(tom);
        }
    }

    public static class Student {

        volatile int myId;
    }

    public static void main(String[] args) throws InterruptedException {
        tom=new Student();
        peter=new Student();
        AtomicIntegerFieldUpdaterDemo r = new AtomicIntegerFieldUpdaterDemo();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("普通变量："+peter.myId);
        System.out.println("升级后的结果"+ tom.myId);
    }
}
