package package4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

//循环队列
public class ArrayQueue {
    private int[] array = new int[10];
    private int size = 0;   // 有效元素个数
    private int front = 0;
    private int rear = 0;
    //入队列(尾插)(时间复杂度O(1))
    public void put(int val) {
        if (size == array.length) {
            throw new RuntimeException("满了");
        }

        array[rear] = val;
        rear = (rear + 1) % array.length;
        /*
        array[rear++] = val;
        if (rear == array.length) {
            rear = 0;
        }
         */
        size++;
    }
    //出队列(头删)(时间复杂度O(1))
    public int take() {
        if (size == 0) {
            throw new RuntimeException("空了");
        }

        /*
        int val = array[front++];
        if (front == array.length) {
            front = 0;
        }
        */
        int val = array[front];
        front = (front + 1) % array.length;
        size--;
        return val;
    }
    //生产者消费者模式
    //用两个线程,生产者线程负责往队列中放入数据,消费者线程负责从队列中取出数据
    //下面的代码数据都是共享的，没有考虑线程安全问题
    private static ArrayQueue queue = new ArrayQueue();//两个线程共享同一个队列

    private static class Producer extends Thread {
        Producer() {
            super("生产者");
        }

        @Override
        public void run() {
            Random random = new Random(20191216);
            while (true) {
                //生产者随机放入数据
                int val = random.nextInt(100);
                try {
                    queue.put(val);//放入10个会出现满了异常
                } catch (RuntimeException e) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        //如果出现满了异常休眠10毫秒
                    } catch (InterruptedException ex) {}
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
                    int val = queue.take();//取了10个会出现空了异常
                    System.out.println(val);
                } catch (RuntimeException e) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                        //如果出现空了异常休眠10毫秒
                    } catch (InterruptedException ex) {}
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.start();
        Customer customer = new Customer();
        customer.start();
    }
}

