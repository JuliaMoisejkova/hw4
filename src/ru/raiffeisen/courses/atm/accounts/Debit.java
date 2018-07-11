package ru.raiffeisen.courses.atm.accounts;

@CashLimit(10000)
public class Debit extends Account {
    public Debit(int balance) {
        super(balance);
    }
}
