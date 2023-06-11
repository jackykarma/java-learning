package lock.reentrantlock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 描述：     演示公平和不公平两种情况
 * 相关内容参考自Java并发编程实战手册2.3小节
 */
public class FairLock {

    public static void main(String[] args) {
        Waiting waiting = new Waiting();
        Thread thread[] = new Thread[10];
        for (int i = 0; i < 10; i++) {
            thread[i] = new Thread(new DoSomething(waiting));
        }
        for (int i = 0; i < 10; i++) {
            thread[i].start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class DoSomething implements Runnable {

    Waiting waiting;

    public DoSomething(Waiting waiting) {
        this.waiting = waiting;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始");
        waiting.printJob(new Object());
        System.out.println(Thread.currentThread().getName() + "干活完毕");
    }
}

class Waiting {

    // 通过参数修改公平和非公平
    private Lock queueLock = new ReentrantLock(false);

    public void printJob(Object document) {
        queueLock.lock();
        try {
            int duration = new Random().nextInt(10) + 1;
            System.out.println(Thread.currentThread().getName() + "正在干活，需要" + duration);
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // TODO:在公平的情况下，若t1释放了锁，那么此锁不会继续交给t1，而是给下一个排队等待获取锁的线程
            queueLock.unlock();
        }
        // TODO:在公平的情况下，t1释放锁之后，此处不会获取到锁，它也要等待；但是若是非公平的情况下，那么unlock之后立即lock就会再次获取到锁，而不会去唤醒其他线程
        queueLock.lock();
        try {
            int duration = new Random().nextInt(10) + 1;
            System.out.println(Thread.currentThread().getName() + "正在干活，需要" + duration + "秒");
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }
}
