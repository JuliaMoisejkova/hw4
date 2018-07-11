package ru.raiffeisen.courses;

import ru.raiffeisen.courses.atm.Atm;
import ru.raiffeisen.courses.atm.accounts.CashLimit;

public class RemoveMeLater {

    public static void main(String[] args) {

        Atm atm = new Atm();

        System.out.println(atm.getClass().getAnnotation(CashLimit.class).value());
    }

}
