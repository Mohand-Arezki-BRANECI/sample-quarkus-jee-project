package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;



@NamedQueries(
        {
                @NamedQuery(name = "getBankObject", query = "SELECT b from Bank b"),

        }
)
@Table(name = "Bank")
@Entity
public class Bank {
    @Id
    @Column(name = "bankId", nullable = false)
    private long bankId;


    @Column(name = "bankname", nullable = false)
    private String bankname;


    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }
}