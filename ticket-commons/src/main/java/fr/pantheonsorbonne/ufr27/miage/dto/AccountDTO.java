package fr.pantheonsorbonne.ufr27.miage.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long accountId;
    private long bankId;
    private String ownerFirstName;
    private String ownerLastName;
    private double balance;
    private String email;
    private String password;

    // Constructors

    public AccountDTO() {
        // Default constructor
    }

    public AccountDTO(long accountId, long bankId, String ownerFirstName, String ownerLastName,
                      double balance, String email, String password) {
        this.accountId = accountId;
        this.bankId = bankId;
        this.ownerFirstName = ownerFirstName;
        this.ownerLastName = ownerLastName;
        this.balance = balance;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

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

    // You may also want to override toString(), equals(), and hashCode() methods as needed.

    // ...

}

