package package2;

public class ThreadUnsafe {
    private static long n = 0L;

    private static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 50000; i++) {
                synchronized (ThreadUnsafe.class) {
                    n++;
                    //3步:读n的值,修改临时的值(+1),写回n的值
                    //如果没有synchronized,一个线程在做这3步过程中
                    //随时可能被另一个线程打断，出现错误,说明不具备原子性
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];//5个线程
        for (int i = 0; i < 5; i++) {
            Thread t = new MyThread();
            t.start();
            threads[i] = t;
        }

        for (int i = 0; i < 5; i++) {
            threads[i].join();
        }

        System.out.println(n);
    }
}
