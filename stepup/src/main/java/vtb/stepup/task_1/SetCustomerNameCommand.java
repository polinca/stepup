package vtb.stepup.task_1;

public class SetCustomerNameCommand implements Command {
    Account account;
    private String oldCustomerName;

    public SetCustomerNameCommand(Account account) {
        this.account = account;
        this.oldCustomerName = account.getCustomerName();
    }

    public void undo() {
        this.account.customerName = this.oldCustomerName;
    }
}
