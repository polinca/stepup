package vtb.stepup.task_1.errors;

public class UndoError extends RuntimeException {
    public UndoError() {
        super("Стек отката пуст");
    }
}
