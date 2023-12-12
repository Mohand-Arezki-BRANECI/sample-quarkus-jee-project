package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "Bank")
@Entity
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankId", nullable = false)
    private long bankId;


    @Column(name = "bankname", nullable = false)
    private String bankname;


    public long getBankId() {
        return bankId;
    }

    public String getBankname() {
        return bankname;
    }

}