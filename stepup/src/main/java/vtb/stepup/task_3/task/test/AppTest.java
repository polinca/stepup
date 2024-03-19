package vtb.stepup.task_3.task.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import vtb.stepup.task_3.task.*;


public class AppTest {


    @Test
    public void testWithoutCacheAnnotation(){
        var fr = new FractionTestWithoutCacheAnnotation(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(3, fr.getInvokeCount());
        num.setNum(5);
        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        Assertions.assertEquals(2, fr.getInvokeCount());
        num.setDenum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(2, fr.getInvokeCount());

    }

    @Test
    public void testValueCaching(){
        var fr = new FractionTestValueCaching(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());

    }

    @Test
    public void testDifferentStatesCaching(){
        var fr = new FractionTestValueCaching(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());

        num.setDenum(5);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(1, fr.getInvokeCount());

        num.setDenum(4);
        System.out.println(num.doubleValue());
        System.out.println(num.doubleValue());
        Assertions.assertEquals(0, fr.getInvokeCount());
    }

    @Test
    public void testDifferentStatesAndArgsCaching(){
        var fr = new FractionTestValueCaching(2, 4);
        Fractionable num = CacheUtils.cache(fr);

        System.out.println(num.doubleValue(5));
        System.out.println(num.doubleValue(5));
        Assertions.assertEquals(1, fr.getInvokeCount());

        System.out.println(num.doubleValue(10));
        Assertions.assertEquals(2, fr.getInvokeCount());
        System.out.println(num.doubleValue(10));
        Assertions.assertEquals(2, fr.getInvokeCount());

        num.setDenum(5);
        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        Assertions.assertEquals(1, fr.getInvokeCount());

        num.setDenum(4);
        System.out.println(num.doubleValue(10));
        System.out.println(num.doubleValue(10));
        Assertions.assertEquals(0, fr.getInvokeCount());

        System.out.println(num.doubleValue(5));
        System.out.println(num.doubleValue(5));
        Assertions.assertEquals(0, fr.getInvokeCount());
    }

    // testMethod() - @Cache(Long.MIN_VALUE)
    @Test
    public void testCleaningCache() {

        var obj = new TestClass();
        var ci = (TestInterface) CacheUtils.cache(obj);
        CacheUtils.startCleaningThreads(1);

        var v1 = ci.testMethod();
        var v2 = ci.testMethod();

        System.out.println(v1);
        System.out.println(v2);
        Assertions.assertEquals(v1, v2);

        var v3 = ci.testMethod();
        System.out.println(v3);
        Assertions.assertNotEquals(v2, v3);

    }

    @Test
    public void testStopCleaning() {
        var fr = new FractionTestMinValueCache(2, 4);
        Fractionable num = CacheUtils.cache(fr);
        CacheUtils.startCleaningThreads(5);

        System.out.println(num.doubleValue());          // InvokeCount увеличивается (1), значение кэшируется
        System.out.println(num.doubleValue(5));   // InvokeCount увеличивается (2), значение кэшируется
        Assertions.assertEquals(2, fr.getInvokeCount());

        CacheUtils.stopCleaningThreads();

        System.out.println(num.doubleValue());          // InvokeCount не увеличивается (2), возвращается кэшированное значение
        System.out.println(num.doubleValue(5));   // InvokeCount не увеличивается (2), возвращается кэшированное значение
        Assertions.assertEquals(2, fr.getInvokeCount());

    }


}
