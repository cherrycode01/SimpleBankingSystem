package banking;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Account {
    Random r = new Random ();
    private String cardNumber;
    private String pin;
    private double balance;

    public String createCardNumber () {
        Random random = new Random ();
        String accountId = String.format ("%09d", (long) (Math.random () * 999999999L));
        String bankId = "400000";
        String checkSum= bankId + accountId;

        String cardNumber = checkSum + generateCheckSumDigit (checkSum);

        this.cardNumber = cardNumber;
        return cardNumber;
    }

    public int generateCheckSumDigit (String checkSum) {
        int sum = 0;
        int remainder = (checkSum.length () + 1) % 2;
        for (int i = 0; i < checkSum.length (); i++) {

            int digit = Integer.parseInt (checkSum.substring (i, (i + 1)));
            if ((i % 2) == remainder) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit - 9);
                }
            }
            sum += digit;
        }
        int mod = sum % 10;
        int checkSumDigit = ((mod == 0) ? 0 : 10 - mod);

        return checkSumDigit;
    }

    public String createPin () {

        String pin = String.format ("%04d", (long) (Math.random () * 9999L));
        this.pin = pin;
        return pin;
    }

    public String getCardNumber () {
        return cardNumber;
    }

    public String getPin () {
        return pin;
    }

    public double getBalance () {
        return balance;
    }

    public void setBalance (double balance) {
        this.balance = balance;
    }
}