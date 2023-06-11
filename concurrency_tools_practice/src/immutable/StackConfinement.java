package immutable;

/**
 * 描述：     演示栈封闭的两种情况，基本变量和对象 先演示线程争抢带来错误结果，然后把变量放到方法内，情况就变了
 */
public class StackConfinement implements Runnable {

    // 共享的变量，它不是线程安全的
    int index = 0;

    public void inThread() {
        // 栈内保护的数据。方法内的局部变量，属于线程私有的，它是线程安全的
        int neverGoOut = 0;
        synchronized (this) {
            for (int i = 0; i < 10000; i++) {
                neverGoOut++;
            }
        }

        System.out.println("栈内保护的数字是线程安全的：" + neverGoOut);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            index++;
        }
        inThread();
    }

    public static void main(String[] args) throws InterruptedException {
        StackConfinement r1 = new StackConfinement();
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r1);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(r1.index);
    }
}
