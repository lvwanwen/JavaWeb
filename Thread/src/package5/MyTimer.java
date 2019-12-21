package package5;

import java.util.concurrent.PriorityBlockingQueue;
//定时器
public class MyTimer {
    //优先级队列要去比较时间的大小，所以要覆写compareTo方法
    private static class MyTimerTask implements Comparable<MyTimerTask> {
        long runAtTime;//任务要执行的时间，即在这个时间后执行任务,绝对时间
        Runnable target;//任务

        public MyTimerTask(long delay, Runnable target) {//delay是相对时间
            this.runAtTime = System.currentTimeMillis() + delay;
            this.target = target;
        }

        @Override
        public int compareTo(MyTimerTask o) {
            if (runAtTime < o.runAtTime) {
                return -1;
            } else if (runAtTime == o.runAtTime) {
                return 0;
            } else {
                return 1;
            }
        }
    }
//优先级阻塞队列，里面放的是多长时间后要执行的任务
    private PriorityBlockingQueue<MyTimerTask> queue
        = new PriorityBlockingQueue<>();
    //多生产者单消费者模型
    //消费者工作线程
    Thread worker = new Worker();
//实例内部类，不加static的原因是方便访问queue
    private class Worker extends Thread {
        @Override
        public void run() {
            //MyTimer.this.queue
            while (true) {
                try {
                    MyTimerTask task = queue.take();
                    //task.runAtTime是这个任务应该运行的时间
                    //说明已经到要运行任务的时间了
                    if (task.runAtTime <= System.currentTimeMillis()) {
                        task.target.run();
                    } else {//没到要运行任务的时间
                        queue.put(task);//先把取出来的这个任务放回去
                        synchronized (this) {
                            //有人唤醒我,我就立即醒来,没人唤醒我,我就等这么长时间
                            wait(task.runAtTime -
                                    System.currentTimeMillis());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    MyTimer() {
        worker.start();
    }
    //schedule要做的只是把任务放入队列中
    public void schedule(Runnable target, long delay) {
        MyTimerTask task = new MyTimerTask(delay, target);
        queue.put(task);
        synchronized (this) {
            notify();
            //假如线程正在等待着,突然有一个任务放入队列了,此时就需要唤醒线程
        }
    }

    public static void main(String[] args) {
        Runnable target = new Runnable() {
            @Override
            public void run() {
                System.out.println("5 秒后");
            }
        };
        MyTimer timer = new MyTimer();
        timer.schedule(target, 5000);
    }
}
