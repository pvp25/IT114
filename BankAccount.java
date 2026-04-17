public abstract class BankAccount {
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double initialDeposit) {
        this.accountHolder = accountHolder;
        this.balance = initialDeposit;

    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    /**
     * Abstract method: Child classes must implement this.
     */
    public abstract void withdraw(double amount);

    // --- GETTERS AND SETTERS ---

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }
}