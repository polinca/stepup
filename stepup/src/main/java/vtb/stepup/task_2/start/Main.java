package vtb.stepup.task_2.start;

import vtb.stepup.task_2.CacheUtils;
import vtb.stepup.task_2.Fraction;
import vtb.stepup.task_2.Fractionable;

public class Main {
    public static void main(String[] args) {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue("XXX"));
        System.out.println(num.doubleValue("XXX"));
        System.out.println(num.doubleValue());
        num.setNum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
       /*
        num.setDenum(0);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());*/
    }
}