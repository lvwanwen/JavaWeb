package package4;

import java.util.Scanner;

public class WaitAndNotify {
    //两个线程要共享同一个object对象
    private static Object object=new Object();
    public static class AThread extends Thread{
        @Override
        public void run() {
            for(int i=0;i<10;i++){
                System.out.println("A正在扫地");
            }
            try {
                synchronized (object){
                    object.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i=0;i<10;i++){
                System.out.println("A正在擦桌子");
            }
        }
    }

    public static void main(String[] args) {
        Thread aThread=new AThread();
        aThread.start();
        //当没有这两句代码时,有可能B线程先执行，此时等待集中还没有线程在等待
        //就提前唤醒了，然后A线程才等待，已经再没有线程去唤醒A线程了
        Scanner sc=new Scanner(System.in);
        sc.nextLine();
        //唤醒线程不能过早，如果在还没有线程在等待中时，过早的唤醒线程，
        //这个时候就会出现先唤醒再等待的效果了.这样就没有必要再去运行wait方法了
        synchronized (object){
            object.notify();
        }
    }
}
