package vtb.stepup.errors;

public class UndoError extends RuntimeException {
    public UndoError() {
        super("Стек отката пуст");
    }
}
