package package5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
//自己实现线程池(只有正式员工,没有临时员工)
public class MyThreadPool {
    private static class Worker extends Thread {
        //只有queue是共享的,但是queue已经是线程安全的了,所以不用再考虑线程安全
        private BlockingQueue<Runnable> queue;
        Worker(BlockingQueue<Runnable> queue) {
            super("我是劳动人民");
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    //如果队列不为空,就从队列中不停的取任务
                    Runnable command = queue.take();
                    command.run();//取到任务之后干活
                }
            } catch (InterruptedException e) {}
        }
    }

    private int corePoolSize = 10;//正式员工最多能有多少人
    private int currentPoolSize = 0;//当前员工个数
    private List<Worker> workerList = new ArrayList<>();
    private BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();//任务队列
    //提交任务
    public void execute(Runnable command) {
        if (currentPoolSize < corePoolSize) {
            Worker worker = new Worker(queue);//雇人
            worker.start();
            workerList.add(worker);//将雇到的人放到人员名单中
            currentPoolSize++;
        }

        queue.add(command);//无论是否雇人了，都要把任务放到队列里面去
    }
    //关闭公司(解雇工人就可以了)
    public void shutDown() throws InterruptedException {
        for (Worker worker : workerList) {
            worker.interrupt();//让工人停止
        }
        for (Worker worker : workerList) {
            worker.join();//让所有的工人停止才行
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyThreadPool pool = new MyThreadPool();
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("吃饭");
            }
        });
        Thread.sleep(10);
        pool.shutDown();
    }
}
