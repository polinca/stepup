package vtb.stepup.task_3.task.start;

import vtb.stepup.task_3.task.CacheUtils;
import vtb.stepup.task_3.task.Fraction;
import vtb.stepup.task_3.task.Fractionable;

public class Main {
    public static void main( String[] args ) throws InterruptedException {
        Fraction fr = new Fraction(2, 4);
        Fractionable num = CacheUtils.cache(fr);
        Fraction fr1 = new Fraction(4, 1);
        Fractionable num1 = CacheUtils.cache(fr1);

        CacheUtils.startCleaningThreads(1);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num1.doubleValue());
        System.out.println(num1.doubleValue());

        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        System.out.println(num1.doubleValue(20));
        System.out.println(num1.doubleValue(20));


        fr.setDenum(5);
        fr1.setDenum(2);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num1.doubleValue());
        System.out.println(num1.doubleValue());

        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        System.out.println(num1.doubleValue(20));
        System.out.println(num1.doubleValue(20));

        fr.setDenum(4);
        fr1.setDenum(1);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num1.doubleValue());
        System.out.println(num1.doubleValue());

        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        System.out.println(num1.doubleValue(20));
        System.out.println(num1.doubleValue(20));

        Thread.sleep(1300);

        System.out.println("===2===");
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num1.doubleValue());
        System.out.println(num1.doubleValue());

        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        System.out.println(num1.doubleValue(20));
        System.out.println(num1.doubleValue(20));

        Thread.sleep(1800);

        System.out.println("===3===");

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num1.doubleValue());
        System.out.println(num1.doubleValue());

        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        System.out.println(num1.doubleValue(20));
        System.out.println(num1.doubleValue(20));

        CacheUtils.stopCleaningThreads();



    }
}