package vtb.stepup.task_2.test;

import lombok.Getter;
import vtb.stepup.task_2.Cache;
import vtb.stepup.task_2.Fractionable;
import vtb.stepup.task_2.Mutator;

public class FractionTestDenumNotAnnotated implements Fractionable {
    @Getter
    private int invokeCount = 0;
    private int num;
    private int denum;

    public FractionTestDenumNotAnnotated(int num, int denum){
        this.num = num;
        this.denum = denum;
        invokeCount = 0;
    }

    @Override
    @Cache
    public double doubleValue() {
        System.out.println("Invoke double value");
        invokeCount++;
        return (double) num / denum;
    }
    @Override
    @Cache
    public double doubleValue(String x) {
        System.out.println("invoke double value "+x);
        return (double) num / denum;
    }
    @Override
    @Mutator
    public void setNum(int num) {
        invokeCount = 0;
        this.num = num;
    }

    @Override
    public void setDenum(int denum){
        invokeCount = 0;
        this.denum = denum;
    }

}
