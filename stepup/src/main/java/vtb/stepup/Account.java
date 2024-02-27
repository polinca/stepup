package vtb.stepup;

import lombok.Getter;
import vtb.stepup.errors.SnapshotExeption;
import vtb.stepup.errors.UndoError;

import java.util.*;


@Getter
public class Account {

    protected String customerName;

    protected HashMap<Currency, Long> currencyMap;


    protected Deque<Command> commandStack = new ArrayDeque();
    private final List<Snapshot> snapshotList = new ArrayList();
    public Account(String customerName) {
        setCustomerName(customerName);
        this.currencyMap = new HashMap();
    }
    public Account(Account account) {
        this.customerName = account.getCustomerName();
        this.currencyMap = account.getCurrencyMap();
        this.commandStack = account.commandStack;
    }
    public void setCustomerName(String customerName) {
        if(  customerName ==null || customerName.isEmpty()  ){
            throw new IllegalArgumentException("Указано пустое Имя");
        }
        SetCustomerNameCommand command = new SetCustomerNameCommand(this);

        this.executeCommand(command);
        this.customerName = customerName;
    }

    public void changeCurrency (Currency currency, Long cnt) {
        if (cnt == null || cnt <= 0L) {
            throw new IllegalArgumentException("Количество не может быть пусто или меньше 0");
        }
        SetCurrencyCommand command = new SetCurrencyCommand(this);

        this.executeCommand(command);
        this.currencyMap.put(currency, cnt);
    }

    public void executeCommand(Command command) {
        this.commandStack.push(command);
    }

    public void undo() {
        if (!this.commandStack.isEmpty()) {
            Command command = (Command)this.commandStack.pop();
            command.undo();
        } else {
            throw new UndoError();
        }
    }

    public void saveState() {
        Snapshot snap = new Snapshot(this.getCustomerName(), this.getCurrencyMap());
        this.snapshotList.add(snap);
    }
    private Snapshot getSnapshot(int index) {
        if (this.snapshotList.isEmpty()) {
            throw new SnapshotExeption("Нет доступных сохранений");
        } else if (index >= 0 && index <= this.snapshotList.size() - 1) {
            return (Snapshot) this.snapshotList.get(index);
        } else {
            throw new SnapshotExeption("Не найдено сохранение по индексу");
        }
    }
    public void loadState(int index) {
        Snapshot snap = this.getSnapshot(index);
        this.customerName = snap.getCustomerName();
        this.currencyMap = snap.getCurrencyMap();
        this.commandStack = new ArrayDeque();
    }
}