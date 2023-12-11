package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Table(name = "BankTransfer")
@Entity
public class BankTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long transaction_id;

    @Column(name = "from_account", nullable = false)
    private long from_account;

    @Column(name = "to_account", nullable = false)
    private long to_account;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "to_account_bank_id", nullable = false)
    private long to_account_bank_id;

    public Long getTransaction_id() {
        return transaction_id;
    }

}

