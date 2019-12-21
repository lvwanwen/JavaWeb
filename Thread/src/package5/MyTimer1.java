package package5;

import java.util.Timer;
import java.util.TimerTask;

//定时器，Java中的使用
public class MyTimer1 {
    public static void main(String[] args) {
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {//接口,需要覆写run方法
            @Override
            public void run() {//run方法为最终时间到了要执行的代码
                System.out.println("5秒钟到了");
            }
        };
        timer.schedule(task,5000);//5秒钟到了之后，去执行任务
    }
}
