package package3;

import java.util.Random;

public class SyncTest2 {
    public static class MyThread extends Thread {
        int[] array;
        //[start,end)
        int start;
        int end;

        MyThread(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }
        //任务已经分配好了，各干各的，所以不用synchronized了
        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                array[i] = array[i] * 3;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Random random=new Random(20191215);
        int[] array=new int[100000];
        for(int i=0;i<array.length;i++){
            array[i]=random.nextInt();
        }
        //线程1计算0到30000的
        MyThread thread = new MyThread(array,0,30000);
        //线程2计算30000到60000的
        MyThread thread1 = new MyThread(array,30000,60000);
        //线程3计算60000到100000的
        MyThread thread2 = new MyThread(array,60000,100000);
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
