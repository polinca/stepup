package vtb.stepup.task_2;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Fraction implements Fractionable{

    private int num;
    private int denum;

    @Mutator
    public void setNum(int num){

        this.num = num;
    }

    @Mutator
    public void setDenum(int denum){

        if (denum == 0){
            throw new IllegalArgumentException("Нельзя делить на 0");
        }

        this.denum = denum;
    }

    @Override
    @Cache
    public double doubleValue() {
        System.out.println("invoke double value");
        return (double) num / denum;
    }
    @Override
    @Cache
    public double doubleValue(String x) {
        System.out.println("invoke double value "+x);
        return (double) num / denum;
    }

}
