import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class User {
    private String userId;
    private String pin;
    private double balance;

    public User(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds.");
        }
    }
}

class Bank {
    private Map<String, User> users;

    public Bank() {
        this.users = new HashMap<>();
        // Adding some sample users
        users.put("user1", new User("user1", "1234", 1000.0));
        users.put("user2", new User("user2", "5678", 500.0));
    }

    public User getUser(String userId) {
        return users.get(userId);
    }

    public void updateUser(String userId, User user) {
        users.put(userId, user);
    }
}

class ATMInterface {
    private User currentUser;

    public ATMInterface(User user) {
        this.currentUser = user;
    }

    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nATM Interface");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Balance: $" + currentUser.getBalance());
                    break;
                case 2:
                    System.out.print("Enter deposit amount: $");
                    double depositAmount = scanner.nextDouble();
                    currentUser.deposit(depositAmount);
                    System.out.println("Deposit successful. New balance: $" + currentUser.getBalance());
                    break;
                case 3:
                    System.out.print("Enter withdrawal amount: $");
                    double withdrawalAmount = scanner.nextDouble();
                    currentUser.withdraw(withdrawalAmount);
                    System.out.println("Withdrawal successful. New balance: $" + currentUser.getBalance());
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class ATMApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.print("Enter user ID: ");
            String userId = scanner.next();

            System.out.print("Enter PIN: ");
            String pin = scanner.next();

            User user = bank.getUser(userId);

            if (user != null && user.getPin().equals(pin)) {
                System.out.println("Login successful.");
                ATMInterface atmInterface = new ATMInterface(user);
                atmInterface.displayMenu();
            } else {
                System.out.println("Invalid user ID or PIN. Please try again.");
            }

            System.out.print("Do you want to log in with another account? (yes/no): ");
            String choice = scanner.next().toLowerCase();

            if (!choice.equals("yes")) {
                break;
            }
        }

        System.out.println("Thank you for using the ATM.");
    }
}
