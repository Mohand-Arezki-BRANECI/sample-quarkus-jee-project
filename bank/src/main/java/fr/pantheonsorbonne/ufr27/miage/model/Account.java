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

    @Column(name = "bankId", nullable = false, length = 45)
    private String bankId;

    @Column(name = "balance", nullable = false, length = 45)
    private String balance;

    public Integer getAccount_id() {
        return accountId;
    }

    public String getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
