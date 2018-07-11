package ru.raiffeisen.courses.atm.accounts;

@CashLimit(2000)
public class Current extends Account {
    public Current(int balance) {
        super(balance);
    }
}
