package fr.pantheonsorbonne.ufr27.miage.dto;

public class TransactionDTO {
    private String email;
    private String password;
    private long toAccount;

    private long bankId;

    private double amount;

    // Constructors

    public TransactionDTO() {
        // Default constructor
    }

    public TransactionDTO(String email, String password, long toAccount, long bankId, double amount) {
        this.email = email;
        this.password = password;
        this.toAccount = toAccount;
        this.bankId = bankId;
        this.amount = amount;
    }

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
