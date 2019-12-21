package package1;

import java.util.Scanner;
//为什么要使用多线程2说明
public class Scene2 {
    private static long fib(int n) {//时间复杂度为O(2^n)
        if (n < 2) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    private static class MyThread extends Thread {
        private int n;
        //多线程run方法是复写的，不能传参进去，只能通过构造方法传参
        MyThread(int n) {
            this.n = n;
        }
        @Override
        public void run() {
            long result = fib(n);
            System.out.printf("fib(%d) 的计算结果为 %d%n", n, result);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("请输入要计算的 n: ");
            //主线程等着来客人，没人来之前一直在等(n)
            int n = scanner.nextInt();
            //来了客人，就找个人过来干活(计算结果)
            Thread thread = new MyThread(n);
            thread.start();
            //输入一个n之后,MyThread中的run方法计算结果,
            //在结果还没计算出来期间还可以继续输入n
        }
    }
}
