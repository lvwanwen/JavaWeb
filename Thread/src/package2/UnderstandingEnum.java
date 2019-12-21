package package2;

import java.util.Arrays;
//枚举
public class UnderstandingEnum {
    enum Week {
        MON, TUE, WEN, THUS, FRI, SAT, SUN;
    }

    public static void main(String[] args) {
        Week[] values = Week.values();
        System.out.println(Arrays.toString(values));
    }
}
