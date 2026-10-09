import java.util.Scanner;

public class ATM {

    public static void main(String[] args) {

        String mode = System.getProperty("app.mode", "development");

        System.out.println("Running in: " + mode);

        Scanner sc = new Scanner(System.in);

        int correctPin = 1234;
        int balance = 10000;
        int attempts = 0;
        boolean loggedIn = false;

        while (attempts < 3) {

            System.out.print("Enter PIN: ");
            int enteredPin = sc.nextInt();

            if (enteredPin == correctPin) {
                loggedIn = true;
                break;
            }

            attempts++;
            System.out.println("Wrong PIN");

            if (attempts == 3) {
                System.out.println("Card blocked");
            }
        }

        if (!loggedIn) {
            sc.close();
            return;
        }

        int[] transactions = new int[10];
        int count = 0;
        int choice;

        do {

            System.out.println();
            System.out.println("===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("Balance: " + balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    int deposit = sc.nextInt();

                    if (deposit <= 0) {
                        System.out.println("Invalid amount");
                        continue;
                    }

                    balance = balance + deposit;

                    if (count < transactions.length) {
                        transactions[count] = deposit;
                        count++;
                    }

                    System.out.println("Deposit successful");
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    int withdraw = sc.nextInt();

                    if (withdraw <= 0) {
                        System.out.println("Invalid amount");
                        continue;
                    }

                    if (withdraw > balance) {
                        System.out.println("Insufficient balance");
                        continue;
                    }

                    balance = balance - withdraw;

                    if (count < transactions.length) {
                        transactions[count] = -withdraw;
                        count++;
                    }

                    System.out.println("Withdrawal successful");
                    break;

                case 4:
                    System.out.println("===== MINI STATEMENT =====");

                    for (int transaction : transactions) {

                        if (transaction == 0) {
                            continue;
                        }

                        if (transaction > 0) {
                            System.out.println("Deposited: " + transaction);
                        } else {
                            System.out.println("Withdrawn: " + (-transaction));
                        }
                    }

                    System.out.println("Balance: " + balance);
                    break;

                case 5:
                    System.out.println("Thank you");
                    break;
            }

        } while (choice != 5);

        sc.close();
    }
}