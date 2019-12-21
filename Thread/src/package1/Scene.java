package package1;
//为什么要使用多线程1说明
public class Scene {
    private static final long COUNT=1000000000;
    private static class MyThread extends Thread{
        @Override
        public void run() {
            long b=0;
            for(long i=0;i<COUNT;i++){
                b+=i;
            }
        }
    }
    private static void multiThread() throws InterruptedException {//多线程并发
        long begin=System.currentTimeMillis();
        MyThread thread=new MyThread();
        thread.start();
        long a=0;
        for(long i=0;i<COUNT;i++){
            a+=i;
        }
        thread.join();//主线程执行完了等待thread中的线程也执行完再返回
        long end=System.currentTimeMillis();
        System.out.println(end-begin);
    }
    private  static void oneThread(){
        long begin=System.currentTimeMillis();//取当前时间，以毫秒为单位
        long a=0;
        for(long i=0;i<COUNT;i++){
            a+=i;
        }
        long b=0;
        for(long i=0;i<COUNT;i++){
            b+=i;
        }
        long end=System.currentTimeMillis();
        System.out.println(end-begin);
    }
    public static void main(String[] args) throws InterruptedException {
        multiThread();
        oneThread();
    }
}
