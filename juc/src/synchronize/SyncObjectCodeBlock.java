package synchronize;

/**
 * 项目名：1-javacoding
 * 包名：synchronize
 * 文件名：null.java
 * 创建时间：2021/8/13-06:25
 *
 * @author jacky.li
 * 描述： synchronized关键字——对象锁第一种情况：代码块形式
 */
public class SyncObjectCodeBlock implements Runnable {

    private static final SyncObjectCodeBlock sSyncObjectCodeBlock = new SyncObjectCodeBlock();

    private static int sCount = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(sSyncObjectCodeBlock);
        Thread t2 = new Thread(sSyncObjectCodeBlock);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("线程结束:" + sCount);
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println("我是线程 :" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "运行结束");
            for (int i = 0; i < 1000000; i++) {
                sCount++;
            }
        }

    }
}
