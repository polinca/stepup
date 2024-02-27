package vtb.stepup;

import java.util.HashMap;

public class SetCurrencyCommand implements Command {

    Account account;
    private HashMap<Currency, Long> oldСurrencyMap;

    public SetCurrencyCommand(Account account) {
        this.account = account;
        this.oldСurrencyMap = account.getCurrencyMap();
    }

    public void undo() {
        this.account.currencyMap = this.oldСurrencyMap;
    }
}
