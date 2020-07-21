package vn.edu.hnue.backend.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class HomeDTO {
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

    private List<RoomDTO> rooms;

    public HomeDTO(Long id, String homeName, String addressCountry, String addressCity, String addressDistrict, String addressCommunes, String addressDetail, int numberBathRoom, String description, int maxGuest, int currentGuest, long price, int homeArea, Date createdDate, Date updatedDate, boolean isLocked, boolean isDeleted, boolean isChecked) {
        this.id = id;
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
        this.isLocked = isLocked;
        this.isDeleted = isDeleted;
        this.isChecked = isChecked;
    }


    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean locked) {
        isLocked = locked;
    }

    public HomeDTO() {
        createdDate = new Date();
        this.isLocked = true;
        this.isDeleted = false;
        this.isChecked = false;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean checked) {
        isChecked = checked;
    }
}
