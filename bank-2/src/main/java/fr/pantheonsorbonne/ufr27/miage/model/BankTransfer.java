package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Table(name = "BankTransfer")
@Entity
public class BankTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId", nullable = false)
    private Long transactionId;

    @Column(name = "fromAccountId", nullable = false)
    private long fromAccountId;

    @Column(name = "fromBankId", nullable = false)
    private long fromBankId;

    @Column(name = "toAccountId", nullable = false)
    private long toAccountId;

    @Column(name = "toBankId", nullable = false)
    private long toBankId;

    @Column(name = "amount", nullable = false)
    private double amount;

    public void setFromBankId(long fromBankId) {
        this.fromBankId = fromBankId;
    }

    public void setFromAccountId(long fromAccount) {
        this.fromAccountId = fromAccount;
    }

    public void setToAccountId(long toAccount) {
        this.toAccountId = toAccount;
    }

    public void setToBankId(long toBankId) {
        this.toBankId = toBankId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }
}


