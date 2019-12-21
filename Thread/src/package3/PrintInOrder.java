package package3;
//让一直按照one two three的顺序打印
public class PrintInOrder {
    private int i = 0;

    public synchronized void first() {
        if (i == 0) {
            System.out.println("one");
            i = 1;
        }
    }

    public synchronized void second() {
        if (i == 1) {
            System.out.println("two");
            i = 2;
        }
    }

    public synchronized void third() {
        if (i == 2) {
            System.out.println("three");
            i = 0;
        }
    }

    private static class PrintOne extends Thread {
        PrintInOrder object;
        PrintOne(PrintInOrder object) {
            this.object = object;
        }

        @Override
        public void run() {
            while (true) {
                object.first();
            }
        }
    }

    private static class PrintTwo extends Thread {
        PrintInOrder object;
        PrintTwo(PrintInOrder object) {
            this.object = object;
        }

        @Override
        public void run() {
            while (true) {
                object.second();
            }
        }
    }

    private static class PrintThree extends Thread {
        PrintInOrder object;
        PrintThree(PrintInOrder object) {
            this.object = object;
        }

        @Override
        public void run() {
            while (true) {
                object.third();
            }
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
