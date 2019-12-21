package package1;

public class Main extends Thread {
    @Override
    public void run() {
        while(true){
            System.out.println("我是启动起来的线程");
        }
    }
//jconsole java监视和管理控制台
    public static void main(String[] args) {
        Main main=new Main();
        main.start();
        while(true){
            System.out.println("我是主线程");
        }
    }
}
