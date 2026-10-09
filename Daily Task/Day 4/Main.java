import java.util.*;

class BankAccount {

    private String accountNumber;
    private String holderName;
    private double balance;

    private static int accountCount = 0;

    public BankAccount() {
        this("0000", "Unknown", 0);
    }

    public BankAccount(String accountNumber, String holderName) {
        this(accountNumber, holderName, 0);
    }

    public BankAccount(String accountNumber, String holderName, double balance) {
        if (balance < 0) {
            balance = 0;
        }

        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;

        accountCount++;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
            return true;
        }

        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public static int getAccountCount() {
        return accountCount;
    }

  
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BankAccount)) {
            return false;
        }

        BankAccount account = (BankAccount) obj;

        return accountNumber.equals(account.accountNumber);
    }

   
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

class BankService {

    public void showBalance(BankAccount account) {
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Holder Name: " + account.getHolderName());
        System.out.println("Balance: " + account.getBalance());
    }

    public void deposit(BankAccount account, double amount) {
        account.deposit(amount);
        System.out.println("Deposit successful");
    }

    public void withdraw(BankAccount account, double amount) {

        boolean result = account.withdraw(amount);

        if (result) {
            System.out.println("Withdraw successful");
        } else {
            System.out.println("Withdraw failed");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        BankAccount account1 =
                new BankAccount("101", "Subhi", 5000);

        BankAccount account2 =
                new BankAccount("102", "Kumar");

        BankAccount account3 =
                new BankAccount();

        BankService service = new BankService();

        System.out.println("Bank Account Details");
        System.out.println();

        service.showBalance(account1);

        System.out.println();

        service.deposit(account1, 2000);

        System.out.println();

        service.withdraw(account1, 1000);

        System.out.println();

        service.showBalance(account1);

        System.out.println();

        service.withdraw(account1, 10000);

        System.out.println();

        System.out.println("Total Accounts: "
                + BankAccount.getAccountCount());

        System.out.println();

        System.out.println("Account 1 equals Account 2: "
                + account1.equals(account2));

        System.out.println("Account 1 hashCode: "
                + account1.hashCode());

        System.out.println("Account 2 hashCode: "
                + account2.hashCode());
    }
}