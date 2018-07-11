package ru.raiffeisen.courses;

import ru.raiffeisen.courses.atm.Atm;
import ru.raiffeisen.courses.atm.accounts.Credit;
import ru.raiffeisen.courses.atm.accounts.Current;
import ru.raiffeisen.courses.atm.accounts.Debit;
import ru.raiffeisen.courses.atm.accounts.OperationLimit;
import ru.raiffeisen.courses.atm.logic.BusinessLogic;
import ru.raiffeisen.courses.atm.user.UserAccounts;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Atm atm = new Atm();
        UserAccounts userAccounts = new UserAccounts(new Current(1000), new Debit(1500), new Credit(-2500));

        Scanner scanner = new Scanner(System.in);
        Integer counter = 0;
        String i;

        do {
            System.out.println("Выберите тип счета для работы  цифрой 1- текущий, 2- дебетовый, 3- кредитный , 0- выход : ");

            i = scanner.next();

            switch (i) {
                case "0":
                    i = "111";
                    break;

                case "1":

                    BusinessLogic.accountOperation(userAccounts.getCurrent(), scanner, atm, userAccounts);
                    counter++;
                    break;

                case "2":
                    if (userAccounts.getCredit().getBalance() > -20000) {
                        BusinessLogic.accountOperation(userAccounts.getDebit(), scanner, atm, userAccounts);
                    } else {
                        System.out.println("Баланс кредитного счета менее -20000, работа с Дб счетом запрещена");
                        i = "111";
                    }
                    counter++;
                    break;

                case "3":
                    BusinessLogic.accountOperation(userAccounts.getCredit(), scanner, atm, userAccounts);
                    counter++;
                    break;

                default:
                    System.out.println("Неверный ввод! Выберите тип счета для работы  цифрой 1- текущий, 2- дебетовый, 3- кредитный, 0- выход : ");
                    i = scanner.next();
            }
        } while (i != "111" && counter < atm.getClass().getAnnotation(OperationLimit.class).value());
    }
}
