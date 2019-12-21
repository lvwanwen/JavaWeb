package package5;

import java.util.Random;
import java.util.concurrent.TimeUnit;
//单生产者多消费者
//生产者满了就wait,等待消费者来唤醒它,消费者空了就wait,等待生产者来唤醒它
public class ArrayQueue2 {
    private int[] array = new int[10];
    private volatile int size = 0;   // 有效元素个数
    private int front = 0;
    private int rear = 0;

    public synchronized void put(int val) throws InterruptedException {
        while (size == array.length) {
            wait();//等待的是当前对象
        }

        array[rear] = val;
        rear = (rear + 1) % array.length;
        size++;
        notifyAll();
    }

    public synchronized int take() throws InterruptedException {
        while (size == 0) {//wait出来发现size还是等于0，就继续等待
            wait();//等待的是当前对象
        }

        int val = array[front];
        front = (front + 1) % array.length;
        size--;
        notifyAll();
        return val;
    }

    public int getSize() {
        return size;
    }

    private static ArrayQueue2 queue = new ArrayQueue2();

    private static class Producer extends Thread {
        Producer() {
            super("生产者");
        }

        @Override
        public void run() {
            Random random = new Random(20191216);
            while (true) {
                int val = random.nextInt(100);
                try {
                    queue.put(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Customer extends Thread {
        Customer() {
            super("消费者");
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int val = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Producer producer = new Producer();
        producer.start();//一个生产者
        for (int i = 0; i < 10; i++) {//10个消费者
            Customer customer = new Customer();
            customer.start();
        }

        while (true) {
            System.out.println(queue.getSize());
            TimeUnit.SECONDS.sleep(1);//每隔一秒钟打印一下队列中的个数
        }
    }
}
