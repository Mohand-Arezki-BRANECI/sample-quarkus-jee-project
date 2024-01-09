package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
@NamedQueries(
        {
                @NamedQuery(name = "getAllAccounts", query = "SELECT a from Account a"),

        }
)
@Table(name = "Account")
@Entity
public class Account {
    @Id
    @Column(name = "accountId", nullable = false)
    private long accountId;

    @Column(name = "ownerFirstName", nullable = false, length = 45)
    private String ownerFirstName;
    @Column(name = "ownerLastName", nullable = false, length = 45)
    private String ownerLastName;

    @Column(name = "email", nullable = false, length = 45)
    private String email;
    @Column(name = "password", nullable = false, length = 45)
    private String password;


    @Column(name = "balance", nullable = false, length = 45)
    private double balance;

    @ManyToOne
    @JoinColumn(name = "bankId", nullable = false)
    private Bank bank;

    public long getAccount_id() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
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
}
