package package5;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//阻塞式队列(生产者消费者模型)
public class BlockingQueueDemo {
    //ArrayBlockingQueue就是循环式阻塞队列，此处容量为10
    //LinkedBlockingQueue为链表实现阻塞队列，这个没有容量限制，是无上限的
    //PriorityBlockingQueue为优先级阻塞队列，里面是用堆实现的
    static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    private static long fib(int n) {
        if (n < 2) {
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }

    private static class Customer extends Thread {
        @Override
        public void run() {
            //消费者从阻塞队列中取，然后通过取到的数来计算斐波那契数
            while (true) {
                try {
                    int n = queue.take();
                    long result = fib(n);
                    System.out.printf("fib(%d) = %d%n", n, result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //一个生产者，10个消费者
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.start();
        }
        //主线程通过Scanner读输入，读到之后放到阻塞队列中
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入 n: ");
            int n = scanner.nextInt();
            queue.put(n);
        }
    }
}
