package vtb.stepup.test;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import vtb.stepup.Account;
import vtb.stepup.Currency;
import vtb.stepup.errors.SnapshotExeption;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class AccountsTest {
    private static String testName1 = "Владимир Путин";
    private static String testName2 = "Дональд Трамп";



    @Test
    @DisplayName("Простое успешное создание Account")
    public void createAccountSimpleSuccsess(){
       Account account = new Account(testName1);
       assertEquals(testName1,account.getCustomerName());
    }


    @Test
    @DisplayName("Неуспешное создание Account по проверке пустого имени")
    public void createAccountEmptyNameFail(){
        assertThrows(IllegalArgumentException.class,() -> new Account(""));
    }

    @Test
    @DisplayName("Неуспешное изменение имени клиента Account по проверке пустого имени")
    public void setNullCustomerNameFail() {
        Account account = new Account(testName2);
        assertThrows(IllegalArgumentException.class, () -> {
            account.setCustomerName("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            account.setCustomerName(null);
        });
    }
    @Test
    @DisplayName("Установка нового имени")
    public void setNewCustomerNameSuccsess () {
        Account account = new Account(testName1);
        account.setCustomerName(testName2);
        assertEquals(testName2, account.getCustomerName());
    }


    @Test
    @DisplayName("ПроверкаОграничений на установку количества валюты")
    public void checkChangeCurrencyWithIncorrectAmountFails() {
        Account account = new Account(testName1);

        assertThrows(IllegalArgumentException.class, () -> {
            account.changeCurrency(Currency.RUB, 0L);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            account.changeCurrency(Currency.RUB, -1L);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            account.changeCurrency(Currency.RUB, (Long)null);
        });
    }

    @Test
    @DisplayName("Проверка отката")
    public void checkUndo() {
        Account account = new Account(testName1);


        Account accountEdit1 =  new Account(account); ;
        account.changeCurrency(Currency.RUB, 1L);
        Account accountEdit2 = new Account(account);
        account.setCustomerName(testName2);
        Account accountEdit3 = new Account(account);
        account.changeCurrency(Currency.USD, 666L);

        account.undo();
        assertEquals(accountEdit3.getCustomerName(), account.getCustomerName());
        assertEquals(accountEdit3.getCurrencyMap(), account.getCurrencyMap());
        account.undo();
        assertEquals(accountEdit2.getCustomerName(), account.getCustomerName());
        assertEquals(accountEdit2.getCurrencyMap(), account.getCurrencyMap());

        account.undo();
        assertEquals(accountEdit1.getCustomerName(), account.getCustomerName());
        assertEquals(accountEdit1.getCurrencyMap(), account.getCurrencyMap());

    }


    @Test
    @DisplayName("Проверка сохранений и загрузки их")
    public void checkSaveAndLoad() {
        Account account = new Account(testName1);
        account.changeCurrency(Currency.RUB, 1L);


        Account accountSnap1 = new Account(account);
        account.saveState();

        account.setCustomerName(testName2);
        account.changeCurrency(Currency.USD, 2L);
        Account accountSnap2 = new Account(account);
        account.saveState();

        account.loadState(0);
        assertEquals(accountSnap1.getCustomerName(), account.getCustomerName());
        assertEquals(accountSnap1.getCurrencyMap(), account.getCurrencyMap());


        account.loadState(1);
        assertEquals(accountSnap2.getCustomerName(), account.getCustomerName());
        assertEquals(accountSnap2.getCurrencyMap(), account.getCurrencyMap());

        assertThrows(SnapshotExeption.class, ()-> account.loadState(999));
    }
}