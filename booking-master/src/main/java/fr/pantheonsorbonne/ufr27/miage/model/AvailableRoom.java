package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

import java.util.Date;

@Table(name = "AvailableRoom")
@Entity

public class AvailableRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availableRoomId", nullable = false)
    private Integer id;

    @Column(name = "numberFreeRooms")
    private Integer numberFreeRooms;

    @Column(name = "startDate")
    private Date startDate;

    @Column(name = "endDate")
    private Date endDate;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberFreeRooms() {
        return numberFreeRooms;
    }
    public void setHotelName(Integer numberFreeRooms) {
        this.numberFreeRooms = numberFreeRooms;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
