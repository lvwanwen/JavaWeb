package package1;
//中断线程
public class HowThreadDie {
    private static class Worker extends Thread {
        private volatile boolean running = true;

        public void quit() {
            running = false;
        }
//这种方法工人正在sleep时是收不到通知的，只有工人醒了才能看到通知停下来
        @Override
        public void run() {
            while (running) {
                System.out.println("挖煤");
                try {
                    Thread.sleep(20 * 1000);//工人每挖一次煤歇20秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();
        Thread.sleep(10 * 1000);//先休息10秒钟再通知工人
        System.out.println("媳妇儿生了，赶紧回家");
        worker.quit();//改变running的状态
        worker.join();//只有join结束才意味着工人真正停下来了
        System.out.println("他回去了");
    }
}
