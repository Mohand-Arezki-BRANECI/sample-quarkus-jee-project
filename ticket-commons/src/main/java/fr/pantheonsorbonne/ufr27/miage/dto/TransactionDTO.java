package fr.pantheonsorbonne.ufr27.miage.dto;

public class TransactionDTO {
    private String email;
    private String password;
    private long toAccountId;

    private long toBankId;



    private long fromAccountId;

    private long fromBankId;
    private double amount;

    // Constructors

    public TransactionDTO() {
        // Default constructor
    }

    public TransactionDTO(String email, String password, long toAccount, long toBankId, long fromAccount, long fromBankId, double amount) {
        this.email = email;
        this.password = password;
        this.toAccountId = toAccount;
        this.toBankId = toBankId;
        this.fromBankId = fromBankId;
        this.fromAccountId = fromAccount;
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

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public long getToBankId() {
        return toBankId;
    }

    public void setToBankId(long bankId) {
        this.toBankId = bankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public long getFromBankId() {
        return fromBankId;
    }

    public void setFromBankId(long fromBankId) {
        this.fromBankId = fromBankId;
    }
}

