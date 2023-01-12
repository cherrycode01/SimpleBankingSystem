package banking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Account> accountList = new ArrayList<> ();

    public static void main (String[] args) throws SQLException {
        AccountRep  accRep = new AccountRep ("card.s3db");
        Scanner sc = new Scanner (System.in);

        int choice = -1;

        while (choice != 0) {
            System.out.println ("\n" + "1. Create an account\n" + "2. Log into account\n" + "0. Exit");
            choice = sc.nextInt ();

            switch (choice) {
                case 1:
                    Account random = new Account ();
                    random.createCardNumber ();
                    random.createPin ();
                    AccountRep.insert(random);
                    accountList.add (random);
                    printCardAndPin (random);
                    break;

                case 2:

                    System.out.println ("Enter your card number: ");
                    String cardNumber = sc.next ();
                    System.out.println ("Enter your PIN: ");
                    String pinNumber = sc.next ();
                    random = findAccount (cardNumber, pinNumber);

                    if (random != null) {
                        System.out.println ("You have successfully logged in!");

                        int ch = -1;

                        while (ch != 0) {
                            System.out.println ("1. Balance\n" + "2. Log out\n" + "0. Exit");
                            ch = sc.nextInt ();

                            if (ch == 1) {
                                System.out.println ("Balance: " + random.getBalance ());
                            } else if (ch == 2) {
                                ch = 0;
                                System.out.println ("You have successfully logged out!");
                            } else if (ch == 0) {
                                choice = 0;
                                System.out.println ("Bye!");
                            }
                        }
                    } else {
                        System.out.println ("Wrong card number or PIN!");
                    }
                case 0:
                    System.out.println ("Bye!");
                    break;
            }
        }
    }

    public static void printCardAndPin (Account random) {
        System.out.println ("Your card has been created");
        System.out.println ("Your card number:");
        System.out.println (random.getCardNumber ());
        System.out.println ("Your card PIN:");
        System.out.println (random.getPin ());

    }

    public static Account findAccount (String cardNumber, String pinNumber) {
        for (Account a : accountList) {
            if (cardNumber.equals (a.getCardNumber ()) && pinNumber.equals (a.getPin ())) {
                return a;
            }
        }
        return null;
    }

    public static Account findAccount (String cardNumber) {
        for (Account a : accountList) {
            if (cardNumber.equals (a.getCardNumber ())) {
                return a;
            }
        }
        return null;
    }

    public static void addIncome (Account currAccount, double income) {
        Scanner scanner = new Scanner (System.in);
        System.out.println("Enter income:");
        double income = scanner.nextInt();
    }
}