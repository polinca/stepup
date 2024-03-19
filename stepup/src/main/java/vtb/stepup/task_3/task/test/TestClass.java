package vtb.stepup.task_3.task.test;


import vtb.stepup.task_3.task.Cache;

public class TestClass implements TestInterface{
    @Override
    @Cache(0)
    public Object testMethod() {
        return new Object();
    }
}
