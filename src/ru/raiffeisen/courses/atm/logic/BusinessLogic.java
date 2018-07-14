package ru.raiffeisen.courses.atm.logic;

import ru.raiffeisen.courses.atm.Atm;
import ru.raiffeisen.courses.atm.accounts.*;
import ru.raiffeisen.courses.atm.user.UserAccounts;

import java.lang.reflect.Method;
import java.util.Scanner;

public class BusinessLogic {

    private final static String bonus = "2000";
    private static int sumOperationBySession = 0;

    @LoggerAtm
    public static void accountOperation(Account acc, Scanner scanner, Atm atm, UserAccounts userAccounts) {

        try {
            Method method = BusinessLogic.class.getDeclaredMethod("accountOperation",
                    Account.class,
                    Scanner.class,
                    Atm.class,
                    UserAccounts.class);
            if (method.isAnnotationPresent(LoggerAtm.class)) logger(method.getName());
        } catch (NoSuchMethodException e1) {
        }

        System.out.println("Выберите тип операции цифрой: 1- внесение, 2- снятие ; 3-перевод между счетами :");
        String a = scanner.next();
        String _sumCasch;

        int i;
        switch (a)

        {
            case "1":
                _sumCasch = sumCash(scanner);
                sumOperationBySession += Integer.parseInt(_sumCasch);
                if (isOperationLimitReached(atm, sumOperationBySession)) {
                    i = Integer.valueOf(_sumCasch);
                    if (acc instanceof Current && i > 1000000) {
                        atm.incrementBalance(userAccounts.getDebit(), bonus);
                        atm.incrementBalance(acc, _sumCasch);
                    } else {
                        atm.incrementBalance(acc, _sumCasch);
                    }
                    System.out.println(acc.getBalance());
                    System.out.println("внести");
                    System.out.println(acc.getClass().getName());
                    break;
                }
                System.out.println(acc.getBalance());
                System.out.println("внести");
                System.out.println(acc.getClass().getName());
            case "2":
                _sumCasch = sumCash(scanner);
                i = Integer.valueOf(_sumCasch);
                sumOperationBySession += Integer.parseInt(_sumCasch);
                if (isOperationLimitReached(atm, sumOperationBySession)) {
                    i = Integer.valueOf(_sumCasch);
                    atm.decrementBalance(acc, _sumCasch);
                }
                System.out.println(acc.getBalance());
                System.out.println("снять");
                System.out.println(acc.getClass().getName());
                break;

            case "3":
                _sumCasch = sumCash(scanner);
                sumOperationBySession += Integer.parseInt(_sumCasch);
                if (isOperationLimitReached(atm, sumOperationBySession)) {
                    i = Integer.valueOf(_sumCasch);
                    atm.transferBalance(acc, acctTransfer(accountChoice(acc, scanner), userAccounts), _sumCasch);
                    break;
                }
                System.out.println("Баланс текущего счета  =  " + userAccounts.getCurrent().getBalance());
                System.out.println("Баланс дебетового счета  =  " + userAccounts.getDebit().getBalance());
                System.out.println("Баланс кредитного счета  =  " + userAccounts.getCredit().getBalance());
                System.out.println("перевести");
                break;
            default:
                System.out.println("Неверный ввод");
                System.out.println("Выберите тип операции цифрой: 1- внесение, 2- снятие ; 3-перевод между счетами :");
                a = scanner.next();
                break;
        }

    }

    @LoggerAtm
    private static String sumCash(Scanner scanner) {

        try {
            Method method = BusinessLogic.class.
                    getDeclaredMethod(
                            "sumCash",
                            Scanner.class);
            if (method.isAnnotationPresent(LoggerAtm.class)) logger(method.getName());
        } catch (Exception e) {
        }
        System.out.println("Введите сумму цифрами");
        return scanner.next();
    }

    @LoggerAtm
    private static String accountChoice(Account account, Scanner scanner) {
        try {
            Method method = BusinessLogic.class.
                    getDeclaredMethod(
                            "accountChoice",
                            Account.class,
                            Scanner.class);
            if (method.isAnnotationPresent(LoggerAtm.class)) logger(method.getName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        if (account instanceof Current) {
            System.out.println("Выберите тип счета, на который будет осуществлен перевод: 2- дебетовый, 3-кредитный");
            return checkAccountTransfer(scanner.next(), scanner);
        } else if (account instanceof Debit) {
            System.out.println("Выберите тип счета, на который будет осуществлен перевод: 1- текущий, 3-кредитный");
            checkAccountTransfer(scanner.next(), scanner);
            return scanner.next();
        } else {
            System.out.println("Выберите тип счета, на который будет осуществлен перевод: 1- текущий, 2-дебетовый");
            checkAccountTransfer(scanner.next(), scanner);
            return scanner.next();
        }
    }

    @LoggerAtm
    private static Account acctTransfer(String instance, UserAccounts userAccounts) {
        try {
            Method method = BusinessLogic.class.
                    getDeclaredMethod("acctTransfer",
                            String.class,
                            UserAccounts.class);
            if (method.isAnnotationPresent(LoggerAtm.class)) logger(method.getName());
        } catch (Exception e1) {
        }
        switch (instance) {
            case "1":
                return userAccounts.getCurrent();
            case "2":
                return userAccounts.getDebit();
            case "3":
                return userAccounts.getCredit();
            default:
                return null;
        }
    }

    @LoggerAtm
    private static String checkAccountTransfer(String accountType, Scanner scanner) {
        try {
            Method method = BusinessLogic.class.
                    getDeclaredMethod(
                            "checkAccountTransfer",
                            String.class,
                            Scanner.class);
            if (method.isAnnotationPresent(LoggerAtm.class)) logger(method.getName());
        } catch (Exception e1) {
        }
        while (!accountType.equals("1") && !accountType.equals("2") && !accountType.equals("3")) {
            System.out.println("Неверный ввод типа счета.Введите тип счета еще раз");
            accountType = scanner.next();
        }
        return accountType;
    }

    @LoggerAtm
    private static boolean isOperationLimitReached(Atm atm, int sum) {

        return sum <= atm.getClass().getAnnotation(CashLimit.class).value();
    }

    private static void logger(String nameMetod) {
        System.out.println("Выполняется метод " + nameMetod);
    }
}
