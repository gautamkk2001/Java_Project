
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.*;


class Account {
    private String accountNumber;
    private String accountHolderName;
    private String accountType;
    private double balance;
    private List<Transaction> transactions;

    public Account(String accountNumber, String accountHolderName, String accountType, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add(new Transaction(accountNumber, "Deposit", amount));
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add(new Transaction(accountNumber, "Withdraw", amount));
            System.out.println("Withdrawal successful.");
            System.out.println("---------------------------");
        } else {
            System.out.println("Insufficient balance or invalid amount.");
             System.out.println("---------------------------");
        }
    }

    public void transfer(Account toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            toAccount.deposit(amount);
            transactions.add(new Transaction(accountNumber, "Transfer", amount));
            System.out.println("Transfer successful.");
            System.out.println("---------------------------");
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayAccountDetails() {
        System.out.println("---------------------------");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + balance);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

class Transaction {
    private static int transactionCounter = 1000;
    private String transactionId;
    private String accountNumber;
    private String transactionType;
    private double amount;
    private String date;

    public Transaction(String accountNumber, String transactionType, double amount) {
        this.transactionId = "TXN" + (++transactionCounter);
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    // @Override
    public String toString() {
             return  "Transaction ID: " + transactionId +
                ", Account Number: " + accountNumber +
                ", Type: " + transactionType +
                ", Amount: $" + amount +
                ", Date: " + date;
    }
}


class Bank {
    private List<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void createAccount(String accountNumber, String accountHolderName, String accountType, double initialBalance) {
        Account account = new Account(accountNumber, accountHolderName, accountType, initialBalance);
        accounts.add(account);
        System.out.println("Account created successfully.");
        System.out.println("---------------------------");
    }

    public void deleteAccount(String accountNumber) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            accounts.remove(account);
            System.out.println("Account deleted successfully.");
             System.out.println("---------------------------");
        } else {
            System.out.println("Account not found.");
             System.out.println("---------------------------");
        }
    }

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            // if (accountNumber.equals(account.getAccountNumber())) {
                return account;
        //   }
        }
        return null;
    }

    public void displayAllAccounts() {
        for (Account account : accounts) {
            account.displayAccountDetails();
            System.out.println();
        }
    }
}


public class Main {
    private static Bank bank = new Bank();

    public static void main(String[] args) {
       
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    deleteAccount(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    withdraw(scanner);
                    break;
                case 5:
                    transfer(scanner);
                    break;
                case 6:
                    checkBalance(scanner);
                    break;
                case 7:
                    viewTransactionHistory(scanner);
                    break;
                case 8:
                    bank.displayAllAccounts();
                    break;
                case 9:
                   
                    System.out.println("Exiting. Data saved to file.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 9);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("");
        System.out.println("");
        System.out.println(" ");
        System.out.println("--------------------------");
        System.out.println("Bank Management System");
        System.out.println("--------------------------");
        System.out.println("1. Create Account");
        System.out.println("2. Delete Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer");
        System.out.println("6. Check Balance");
        System.out.println("7. View Transaction History");
        System.out.println("8. Display All Accounts");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter Account Type (Savings/Current): ");
        String accountType = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double initialBalance = scanner.nextDouble();
        System.out.print("Enter Phone Number to find Account: ");
        int phone = scanner.nextInt();
        System.out.print("Enter OTP to proceed:  ");
        int otp = scanner.nextInt();
        bank.createAccount(accountNumber, accountHolderName, accountType, initialBalance);
    }

    private static void deleteAccount(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter OTP to proceed:  ");
        int otp = scanner.nextInt();
        bank.deleteAccount(accountNumber);
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Amount to Deposit: ");
        double amount = scanner.nextDouble();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Amount to Withdraw: ");
        double amount = scanner.nextDouble();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Enter Your Account Number: ");
        String fromAccountNumber = scanner.nextLine();
        System.out.print("Enter Recipient's Account Number: ");
        String toAccountNumber = scanner.nextLine();
        System.out.print("Enter Amount to Transfer: ");
        double amount = scanner.nextDouble();
        Account fromAccount = bank.getAccount(fromAccountNumber);
        Account toAccount = bank.getAccount(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    private static void checkBalance(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            System.out.println("Balance: $" + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void viewTransactionHistory(Scanner scanner) {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        Account account = bank.getAccount(accountNumber);
        if (account != null) {
            for (Transaction transaction : account.getTransactions()) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("Account not found.");
        }
    }
}

