package package2;

public class WatchThreadState2 {
    private static class MyThread extends Thread{
        @Override
        public void run() {
            while(!Thread.interrupted()){
            }
        }
    }
    //任何代码可以运行的前提是:该代码所在的线程被调度到CPU上了
    public static void main(String[] args)  {
        MyThread thread = new MyThread();
        System.out.println(thread.getState());  //NEW
        thread.start();
        System.out.println(thread.getState());//RUNNABLE
        thread.interrupt();//不会立即停止，此时主线程还在运行，只有抢到CPU才会停止
        while (thread.isAlive()) {
            System.out.println(thread.getState());//RUNNABLE
        }
        System.out.println(thread.getState());//TERMINATED
    }
}
