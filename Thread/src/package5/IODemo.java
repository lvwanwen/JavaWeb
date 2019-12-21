package package5;

import java.io.IOException;
import java.io.PrintWriter;
//写文件
public class IODemo {
    public static void main(String[] args) throws IOException {
        PrintWriter printWriter = new PrintWriter("hello.txt", "UTF-8");
        printWriter.println("你好");
        printWriter.println("我是中国人");
        printWriter.printf("%d: 我进来了%n", 18);
        printWriter.close();
    }
}
