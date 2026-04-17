public class SavingsAccount extends BankAccount {
    public SavingsAccount(String accountHolder, double initialDeposit) {
        super(accountHolder, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && getBalance() - amount >= 0) {
            setBalance(getBalance() - amount);
        }
    }
}