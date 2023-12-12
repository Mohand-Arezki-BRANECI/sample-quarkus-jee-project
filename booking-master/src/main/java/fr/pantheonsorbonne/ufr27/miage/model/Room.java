package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@Table(name = "Room")
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRoom", nullable = false)
    private Integer id;

    @Column(name = "price", nullable = false)
    private Integer price;
    @Column(name = "bedsNumber", nullable = false)
    private Integer bedsNumber;

    @Column(name = "roomType", nullable = false)
    private String roomType;

    @Column(name = "number_Guests", nullable = false)
    private Integer guestsNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
