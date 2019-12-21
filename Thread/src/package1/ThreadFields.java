package package1;

public class ThreadFields {
    public static void main(String[] args) {
        //Thread.currentThread()
        //获取当前线程的Thread对象
        Thread currentThread=Thread.currentThread();
        System.out.println(currentThread.getId());
        System.out.println(currentThread.getName());
        System.out.println(currentThread.getPriority());
        System.out.println(currentThread.getState());
        System.out.println(currentThread.isDaemon());
        System.out.println(currentThread.isAlive());
        System.out.println(currentThread.isInterrupted());
    }
}
