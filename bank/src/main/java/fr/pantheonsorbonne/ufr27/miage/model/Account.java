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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accountId", nullable = false)
    private Integer accountId;

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

    public Integer getAccount_id() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
