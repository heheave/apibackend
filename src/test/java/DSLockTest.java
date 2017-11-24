import com.sjtu.zk.DSLockUtil;
import com.sjtu.zk.DSLocker;

/**
 * Created by xiaoke on 17-10-20.
 */
public class DSLockTest {

    static final String url = "localhost:2181";
    static final String path = "/dslock";
    static final int timeout = 5000;

    public static class UnfairRun implements Runnable {

        public final String name;

        public UnfairRun(String name) {
            this.name = name;
        }

        public void run() {
            DSLocker locker1 = DSLockUtil.unfairLock(url, timeout, path);
            System.out.println(name + " locked");
            System.out.println(name + " sleeping");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " sleep unlock");
            locker1.unlock();
        }
    }

    public static class FairRun implements Runnable {

        public final String name;

        public FairRun(String name) {
            this.name = name;
        }

        public void run() {
            DSLocker locker1 = DSLockUtil.fairLock(url, timeout, path);
            System.out.println(name + " locked");
            System.out.println(name + " sleeping");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + " sleep unlock");
            locker1.unlock();
        }
    }

    public static void main(String[] args) {
        //unfairTest1();
        fairTest1();
    }

    public static void unfairTest1() {
        Thread t1 = new Thread(new UnfairRun("t1"));
        Thread t2 = new Thread(new UnfairRun("t2"));
        Thread t3 = new Thread(new UnfairRun("t3"));
        t1.start();
        t2.start();
        t3.start();
    }

    public static void fairTest1() {
        Thread t1 = new Thread(new FairRun("t1"));
        Thread t2 = new Thread(new FairRun("t2"));
        Thread t3 = new Thread(new FairRun("t3"));
        t1.start();
        t2.start();
        t3.start();
    }
}
