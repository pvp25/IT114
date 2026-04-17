public class CheckingAccount extends BankAccount {
    private final double OVERDRAFT_FEE = 35.00;

    public CheckingAccount(String accountHolder, double initialDeposit) {
        super(accountHolder, initialDeposit);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0) {
            if (getBalance() - amount < 0) {
                setBalance(getBalance() - amount - OVERDRAFT_FEE);
            } else {
                setBalance(getBalance() - amount);
            }
        }
    }
}