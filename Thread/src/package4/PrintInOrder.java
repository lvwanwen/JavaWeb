package package4;

public class PrintInOrder {
    private int i = 0;
    //如果把notifyAll改为notify就会发生下面的问题:
    //调度是随机的，有可能先调度的是2和3，发现i!=0，放在等待集，然后1上来，
    // 打印了1之后，i=1,唤醒的不一定是2，假如唤醒的是3，1放在等待集
    // 3上CPU发现i=1，也进入等待集，此时1 2 3都在等待集中，就会有问题
    public synchronized void first() throws InterruptedException {
        if (i == 0) {
            System.out.println("one");
            i = 1;
            notifyAll();
        }
        wait();//i!=0时放入等待集
    }

    public synchronized void second() throws InterruptedException {
        if (i == 1) {
            System.out.println("two");
            i = 2;
            notifyAll();
        }
        wait();
    }

    public synchronized void third() throws InterruptedException {
        if (i == 2) {
            System.out.println("three");
            i = 0;
            notifyAll();
        }
        wait();
    }

    private static class PrintOne extends Thread {
        PrintInOrder object;
        PrintOne(PrintInOrder object) {
            this.object = object;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    object.first();
                }
            } catch (InterruptedException e) {}
        }
    }

    private static class PrintTwo extends Thread {
        PrintInOrder object;
        PrintTwo(PrintInOrder object) {
            this.object = object;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    object.second();
                }
            } catch (InterruptedException e) {}
        }
    }

    private static class PrintThree extends Thread {
        PrintInOrder object;
        PrintThree(PrintInOrder object) {
            this.object = object;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    object.third();
                }
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        PrintInOrder object = new PrintInOrder();
        Thread t1 = new PrintOne(object);
        t1.start();
        Thread t2 = new PrintTwo(object);
        t2.start();
        Thread t3 = new PrintThree(object);
        t3.start();
    }
}
