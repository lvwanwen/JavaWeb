package package3;
//死锁问题,通过jconsole可以去判断是否是死锁问题
//两个线程必须要有两把锁，只有一把锁是不会造成死锁的
public class DeadLock {
    private static Object aLock = new Object();
    private static Object bLock = new Object();
//A线程先去请求aLock，请求到aLock之后歇了3秒，又去请求bLock
    private static class AThread extends Thread {
        @Override
        public void run() {
            synchronized (aLock) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {}
                synchronized (bLock) {
                    System.out.println("A: 吃火锅");
                }
            }
        }
    }
//B线程先请求bLock，请求到bLock之后，又直接去请求aLock
    private static class BThread extends Thread {
        @Override
        public void run() {
            synchronized (bLock) {
                synchronized (aLock) {
                    System.out.println("B: 吃火锅");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AThread aThread = new AThread();
        BThread bThread = new BThread();
        aThread.start();//先让A线程启动
        Thread.sleep(2000);
        //中间休息2秒，保证A一定请求到aLock，但A没请求到bLock
        bThread.start();//再让B线程启动
    }
}
