package vn.edu.hnue.backend.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@Transactional
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String addressCountry;
    @NotBlank
    private String addressCity;
    @NotBlank
    private String addressDistrict;
    @NotBlank
    private String addressCommunes;
    @NotBlank
    private String addressDetail;
    private String roomName;
    private float roomArea;
    private int maxGuest;
    private int numberBathRoom;
    private String description;
    private Long price;
    private boolean isLocked;
    private boolean isDeleted;
    private String imagesID;
    private Date createdDate;
    private Date updatedDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createdUser;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "guest_room",
    joinColumns = @JoinColumn(name = "room_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> guest;

    public Room() {
        this.isLocked = true;
        this.isDeleted = false;
        this.createdDate = new Date();
    }


    public Room(String addressCountry, String addressCity, String addressDistrict, String addressCommunes, String addressDetail, String roomName, float roomArea, int maxGuest, int numberBathRoom, String description, Long price, String imagesID, User createdUser) {
        this.addressCountry = addressCountry;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.addressCommunes = addressCommunes;
        this.addressDetail = addressDetail;
        this.roomName = roomName;
        this.roomArea = roomArea;
        this.maxGuest = maxGuest;
        this.numberBathRoom = numberBathRoom;
        this.description = description;
        this.price = price;
        this.imagesID = imagesID;
        this.createdUser =createdUser;
        this.isLocked = true;
        this.isDeleted = false;
        this.createdDate = new Date();
    }
}
