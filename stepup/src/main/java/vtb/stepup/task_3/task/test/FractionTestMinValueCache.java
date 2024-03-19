package vtb.stepup.task_3.task.test;

import lombok.Getter;
import vtb.stepup.task_3.task.*;


public class FractionTestMinValueCache implements Fractionable {
    @Getter
    @NotInState
    private int invokeCount;
    private int num;
    private int denum;

    public FractionTestMinValueCache(int num, int denum){
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
    public double doubleValue(int param) {
        System.out.println("Invoke double value");
        invokeCount++;
        return (double) param + denum;
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
