package vn.edu.hnue.backend.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@Transactional
public class Home {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String homeName;
    private String addressCountry;
    private String addressCity;
    private String addressDistrict;
    private String addressCommunes;
    private String addressDetail;
    private int numberBathRoom;
    private String description;
    private int maxGuest;
    private int currentGuest;
    private long price;
    private int homeArea;
    private Date createdDate;
    private Date updatedDate;
    private boolean isLocked;
    private boolean isDeleted;
    private boolean isChecked;

    @OneToMany( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();
    @OneToOne
    private User createdUser;

    public Home(String homeName, String addressCountry, String addressCity, String addressDistrict, String addressCommunes, String addressDetail, int numberBathRoom, String description, int maxGuest, int currentGuest, long price, int homeArea, Date createdDate, Date updatedDate) {
        this.homeName = homeName;
        this.addressCountry = addressCountry;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.addressCommunes = addressCommunes;
        this.addressDetail = addressDetail;
        this.numberBathRoom = numberBathRoom;
        this.description = description;
        this.maxGuest = maxGuest;
        this.currentGuest = currentGuest;
        this.price = price;
        this.homeArea = homeArea;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        isLocked = false;
        isDeleted = false;
        isChecked = false;
    }
}
