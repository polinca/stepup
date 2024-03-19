package vtb.stepup.task_3.task.test;

import lombok.Getter;
import vtb.stepup.task_3.task.*;

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
        return 0.5;
    }

    @Override
    public double doubleValue(int param) {
        System.out.println("Invoke double value");
        invokeCount++;
        return (double) param;
    }

    @Override
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
