package vtb.stepup.task_2.test;

import lombok.Getter;
import vtb.stepup.task_2.Fractionable;
import vtb.stepup.task_2.Mutator;

public class FractionTestWithoutCacheAnnotation implements Fractionable {
    @Getter
    private int invokeCount = 0;
    private int num;
    private int denum;

    public FractionTestWithoutCacheAnnotation(int num, int denum){
        this.num = num;
        this.denum = denum;
        invokeCount = 0;
    }

    @Override
    public double doubleValue() {
        System.out.println("Invoke double value");
        invokeCount++;
        return (double) num / denum;
    }
    @Override
    public double doubleValue(String x) {
        System.out.println("invoke double value "+x);
        return (double) num / denum;
    }
    @Override
    public void setNum(int num) {
        invokeCount = 0;
        this.num = num;
    }

    @Override
    @Mutator
    public void setDenum(int denum){
        invokeCount = 0;
        this.denum = denum;
    }


}
