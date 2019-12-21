package package2;

public class YieldDemo {
    private static class MyThread extends Thread {
        private final String message;

        MyThread(String name, String message) {
            super(name);
            this.message = message;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + message);
            }
        }
    }

    private static class MyThread2 extends Thread {
        private final String message;

        MyThread2(String name, String message) {
            super(name);
            this.message = message;
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + ":" + message);
                yield();
            }
        }
    }

    public static void main(String[] args) {
        MyThread thread1 = new MyThread("自私的", "我在唱歌");
        MyThread2 thread2 = new MyThread2("不是那么自私的", "我在唱歌");
        thread1.start();
        thread2.start();
    }
}
