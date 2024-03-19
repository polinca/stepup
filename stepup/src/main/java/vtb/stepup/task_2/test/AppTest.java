package vtb.stepup.task_2.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vtb.stepup.task_2.*;

public class AppTest {
    @Test
    public void testAllMethodsAnnotated()
    {
        FractionTestAllMethodsAnnotated fr = new FractionTestAllMethodsAnnotated(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue("xxxxx"));
        System.out.println(num.doubleValue());
        Assertions.assertEquals(0, fr.getInvokeCount());
        num.setNum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());
        num.setDenum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());
    }

    @Test
    public void testDenumNotAnnotated(){
        FractionTestDenumNotAnnotated fr = new FractionTestDenumNotAnnotated(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());
        num.setNum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());
        num.setDenum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(0, fr.getInvokeCount());
    }

    @Test
    public void testWithoutCacheAnnotation(){
        FractionTestWithoutCacheAnnotation fr = new FractionTestWithoutCacheAnnotation(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(3, fr.getInvokeCount());
        num.setNum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(2, fr.getInvokeCount());
        num.setDenum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(2, fr.getInvokeCount());

    }


}
