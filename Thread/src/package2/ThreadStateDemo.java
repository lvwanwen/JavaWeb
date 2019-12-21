package package2;

public class ThreadStateDemo {
    //线程的状态
    public static void main(String[] args) {
        Thread.State[] values = Thread.State.values();
        for (Thread.State state : values) {
            System.out.println(state);
        }
    }
}
