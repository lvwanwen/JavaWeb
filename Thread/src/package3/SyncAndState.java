package package3;
//synchronized和线程状态之间的关系
public class SyncAndState {
    private synchronized void method() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("" + i + ":" + Thread.currentThread().getName());
        }
    }

    private static class MyThread extends Thread {
        private SyncAndState object;

        MyThread(SyncAndState object, String name) {
            super(name);
            this.object = object;
        }

        @Override
        public void run() {
            synchronized (object) {
                object.method();
            }
        }
    }

    public static void main(String[] args) {
        SyncAndState object = new SyncAndState();
        MyThread thread = new MyThread(object, "我是子线程");
        thread.start();

        object.method();
    }
}
