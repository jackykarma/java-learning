package synchronize;

/**
 * 项目名：1-javacoding
 * 包名：synchronize
 * 文件名：null.java
 * 创建时间：2021/8/12-06:58
 *
 * @author jacky.li
 * 描述：演示在并发访问中不进行同步操作所带来的问题
 */
public class NoSyncOperationError implements Runnable {

    private static final NoSyncOperationError sNoSyncOperationError = new NoSyncOperationError();

    private static int mCount = 0;

    public static void main(String[] args) {
        // 2个线程访问同一个对象的代码run方法
        Thread thread1 = new Thread(sNoSyncOperationError);
        Thread thread2 = new Thread(sNoSyncOperationError);
        thread1.start();
        thread2.start();
        System.out.println("thread1 thread2 start");
        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
        try {
            thread1.join();
            thread2.join();
            System.out.println("mCount:" + mCount);
            System.out.println(thread1.getState());
            System.out.println(thread2.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 100_000; i++) {
            // ++并发原子操作，实际包含3个操作
            // 1. 读取count； 2.将count加1； 3.将count的值写入内存中
            // 在多线程中这三步任何一个步骤都可能被另外一个线程打断，因此导致内部不一致（预期值与实际得到的值不同）
            mCount++;
        }
    }
}
