package package5;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class ArrayQueue {
    //生产者和消费者之间共享同一个队列,因为有共享才会出现线程安全问题
    //队列中有以下四个元素,只有size是生产者和消费者共用的,所以问题出在size上
    //因为size可能会导致生产者生产完了，消费者还没消费的情况
    private int[] array = new int[10];
    //只在此处加volatile是不行的，volatile不能保证原子性
    private volatile int size = 0;//有效元素个数
    private int front = 0;
    private int rear = 0;

    public void put(int val) {
        if (size == array.length) {
            throw new RuntimeException("满了");
        }

        array[rear] = val;
        rear = (rear + 1) % array.length;
        synchronized (this) {
            size++;
        }
    }

    public int take() {
        if (size == 0) {
            throw new RuntimeException("空了");
        }

        int val = array[front];
        front = (front + 1) % array.length;
        synchronized (this) {
            size--;
        }
        return val;
    }
    public int getSize(){
        return size;//加volatile可以保证此处size的可见性
    }

    private static ArrayQueue queue = new ArrayQueue();

    private static class Producer extends Thread {
        Producer() {
            super("生产者");
        }
        //生产了哪些元素放到生产了.txt文件中,消费了哪些元素放到消费了.txt文件中
        //这样好进行对比生产序列和消费序列是否完全相同
        PrintWriter printWriter;
        {
            try {
                printWriter = new PrintWriter("生产了.txt", "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            Random random = new Random(20191216);
            for (int i = 0; i < 1000; i++) {//生产者生产1000个数据
                int val = random.nextInt(100);
                printWriter.println(val);//写入文件
                //如果数据能写进去就break,下次再写下一个数据,如果没写进去说明满了
                //此处已经捕获了异常,然后继续尝试往序列里面写数据
                //这样可以保证一定会往队列里面写1000个数据
                do {
                    try {
                        queue.put(val);
                        break;
                    } catch (RuntimeException e) {
                    }
                } while (true);
            }
            printWriter.close();
        }
    }

    private static class Customer extends Thread {
        PrintWriter printWriter;
        Customer() {
            super("消费者");
        }

        {
            try {
                printWriter = new PrintWriter("消费了.txt", "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                do {
                    try {
                        int val = queue.take();
                        printWriter.println(val);
                        break;
                    } catch (RuntimeException e) {
                    }
                } while (true);
            }
            printWriter.close();
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.start();
        Customer customer = new Customer();
        customer.start();
    }
}
