package package3;

import java.util.Random;
//利用三个线程将array数组中的所有元素*3,index是共享的
public class SyncTest {
    private static int index=0;
    public static class MyThread extends Thread {
        int[] array;

        MyThread(int[] array) {
            this.array = array;
        }

        @Override
        public void run() {
            while (index < array.length) {
                //如果把synchronized放在循环外边,就只有一个线程会抢到锁,
                // 然后自己把所有的都执行完
                //三个线程抢的是同一把锁,保证互斥性
                synchronized (MyThread.class) {
                    //synchronized的时间是很长的,有可能在这期间index>array.length
                    if (index < array.length) {
                        array[index] = array[index] * 3;
                        index++;
                    }
                }
                //synchronized在大括号左边开始请求锁,进入大括号中请求到锁,大括号出来释放锁
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Random random=new Random(20191215);//随机种子
        int[] array=new int[100000];
        for(int i=0;i<array.length;i++){
            array[i]=random.nextInt();
        }
        //构造三个线程
        MyThread thread = new MyThread(array);
        MyThread thread1 = new MyThread(array);
        MyThread thread2 = new MyThread(array);
        thread.start();
        thread1.start();
        thread2.start();
        thread.join();
        thread1.join();
        thread2.join();
        System.out.println(array[0]);
        System.out.println(array[3]);
        System.out.println(array[107]);
        System.out.println(array[323]);
        System.out.println(array[6666]);
        System.out.println(array[88888]);
        System.out.println(array[93192]);
    }
}
