package banking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Account> accountList = new ArrayList<> ();
    static AccountRep  accRep;

    static {
        try {
            accRep = new AccountRep ("card.s3db");
        } catch (SQLException e) {
            throw new RuntimeException (e);
        }
    }

    public static void main (String[] args) throws SQLException {

        Scanner sc = new Scanner (System.in);
        accRep.loadAccountsFromDbToList (accountList);

        int choice = -1;

        while (choice != 0) {
            System.out.println ("\n" + "1. Create an account\n" + "2. Log into account\n" + "0. Exit");
            choice = sc.nextInt ();

            switch (choice) {
                case 1:
                    Account random = new Account ();
                    random.createCardNumber ();
                    random.createPin ();
                    accRep.insert(random);
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
                            System.out.println ("1. Balance\n" + "2. Add income\n" + "3. Do transfer" + "4. Close account\n" + "5. Log out\n" + "0. Exit");
                            ch = sc.nextInt ();

                            if (ch == 1) {
                                System.out.println ("Balance: " + random.getBalance ());
                            } else if (ch == 2) {
                                System.out.println ("Enter income:");
                                double income = sc.nextDouble ();
                                addIncome (random, income);
                            } else if (ch == 3) {
                                System.out.println ("Transfer\n" + "Enter card number:");
                                String cardNumberToTransfer = sc.next ();
                                String cardNumberToTransferWithoutLastDigit = cardNumberToTransfer.substring (0,15);
                                String cardNumberToTransferLastDigit = cardNumberToTransfer.substring (15);
                                int checkLuhn = Account.generateCheckSumDigit (cardNumberToTransferWithoutLastDigit);
                                
                                if (Integer.parseInt (cardNumberToTransferLastDigit) != checkLuhn) {
                                    System.out.println ("Probably you made a mistake in the card number. Please try again!");
                                } else  {
                                    Account toTransfer = findAccount (cardNumberToTransfer);

                                }

                            } else if (ch == 5) {
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

    public static void addIncome (Account random, double income) {
        random.setBalance (random.getBalance () + income);
        accRep.update (random);
        System.out.println ("Income was added!");
    }
}

/*


From Dragan to Everyone 11:56 AM
String sql = "UPDATE card SET
From Dragan to Everyone 12:22 PM
public void loadAccountsFromDbToList(List<Account>accountsList){
        String sql = "SELECT * FROM card";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                Account a = new Account();
                a.setCardNumber(rs.getString("number"));
                a.setPin(rs.getString("pin"));
                a.setBalance(rs.getInt("balance"));
                accountsList.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(accountsList.toString());
    }

From Dragan to Everyone 12:33 PM
public static void transferMoney(Account current, Account toTransfer, double money) {
        if (current.getBalance() < money) {
            System.out.println("Not enough money!");
        } else {
            current.setBalance(current.getBalance() - money);
            toTransfer.setBalance(toTransfer.getBalance() + money);
            accDao.updateAccount(current);
            accDao.updateAccount(toTransfer);
            System.out.println("Success!");
        }
    }

    }

 */