package vn.edu.hnue.backend.model.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private String addressCountry;
    private String addressCity;
    private String addressDistrict;
    private String addressCommunes;
    private String addressDetail;
    private String roomName;
    private float roomArea;
    private int maxGuest;
    private int numberBathRoom;
    private String description;
    private long price;
    private boolean isLocked;
    private boolean isDeleted;
    private List<String> images;

    public RoomDTO() {
        this.isLocked = false;
        this.isDeleted = false;
    }

    public RoomDTO(Long id, String addressCountry, String addressCity, String addressDistrict, String addressCommunes, String addressDetail, String roomName, float roomArea, int maxGuest, int numberBathRoom, String description, long price, List<String> images) {
        this.id = id;
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
        this.images = images;
    }


    public boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
