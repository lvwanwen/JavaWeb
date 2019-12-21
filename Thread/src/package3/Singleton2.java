package package3;
//懒汉模式
public class Singleton2 {
    private int field ;
    private static volatile Singleton2 instance=null;
    //volatile保证创建instance重排序性,synchronized只能保证大括号内的重排序性，
    //加上volatile保证创建instance整体的重排序性
    private Singleton2(){//用private修饰表示不允许外部类构造对象
        field = 100;
    }
    //把构造方法设为private是为了不允许外部类构造对象
    public static Singleton2 getInstance(){
        //不把synchronized放在方法上是为了提升效率,如果把synchronized
        // 放在静态方法上意味着每次建造对象都要争抢锁,把synchronized
        // 放在里面只有在初始化对象的时候才争抢锁,之后就再也不用抢锁了
        if(instance==null){
            synchronized (Singleton2.class){
                //请求锁的过程中有可能发生调度，所以要二次判断
                if(instance==null){
                    instance=new Singleton2();
                }
            }
        }
        return instance;
    }
    private static class MyThread extends Thread {
        @Override
        public void run() {
            Singleton2 s = Singleton2.getInstance();
            System.out.println(s + ":" + s.field);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new MyThread().start();
        }
    }
}
