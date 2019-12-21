package package5;

import java.util.concurrent.*;
//线程池
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //任务队列中最多允许放30个任务
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(30);
        ExecutorService pool = new ThreadPoolExecutor(
                10,
                20,
                5,
                TimeUnit.SECONDS,//临时工最多可以空闲5秒钟
                queue
        );
        //提交一个任务,只要以提交任务上面的线程池就开始工作起来了
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("吃饭");
            }
        });
        //pool.shutdown()关闭公司
        //Executors.newCachedThreadPool();没有正式工,全是临时工
        //Executors.newFixedThreadPool();没有临时工,全是正式工,是固定大小的线程池
    }
}
