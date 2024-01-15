package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Table(name = "BankTransfer")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transferId", nullable = false)
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

    @Column(name = "transactionPurpose", nullable = false)
    private String transactionPurpose;

    @Column(name = "reservationId", nullable = false)
    private String reservationId;

    public long getFromBankId() {
        return fromBankId;
    }

    public void setFromBankId(long fromBankId) {
        this.fromBankId = fromBankId;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(long fromAccount) {
        this.fromAccountId = fromAccount;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccount) {
        this.toAccountId = toAccount;
    }

    public long getToBankId() {
        return toBankId;
    }

    public void setToBankId(long toBankId) {
        this.toBankId = toBankId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public String getTransactionPurpose() {
        return transactionPurpose;
    }
    public void setTransactionPurpose(String transactionPurpose) {
        this.transactionPurpose = transactionPurpose;
    }

    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
}


