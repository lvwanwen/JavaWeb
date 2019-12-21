package package1;

public class AnonymousAndLambda {
    public static void main(String[] args) {
        //匿名类
        //1.创建一个Thread子类，但是这个子类没有名字；
        //2.直接拿着这个子类构造一个对象出来
        Thread t=new Thread(){//直接Alt+Enter就可以替换成lambda表达式
            @Override
            public void run() {
            }
        };
        t.start();
        //lambda表达式
        Thread t2= new Thread(
                () -> { }//直接Alt+Enter就可以替换成匿名类
                );
        t2.start();
    }
}
