package package1;

public class HowThreadDie2 {
    private static class Worker extends Thread {
        @Override
        public void run() {
            while (!Thread.interrupted()) {//没在睡眠时中断
                System.out.println("挖煤");
                try {
                    Thread.sleep(50 * 1000);
                } catch (InterruptedException e) {//正在睡眠时中断
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.start();
        Thread.sleep(3 * 1000);
        System.out.println("媳妇儿生了，赶紧回家");
        worker.interrupt();
        worker.join();
        System.out.println("他回去了");
    }
}
