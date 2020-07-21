package vn.edu.hnue.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String customerFullName;
    private String customerPhoneNumber;
    private String email;
    private int numberGuest;
    private Long price;
    private Date startDateRent;
    private Date stopDateRent;
    private Date createdDate;
    private boolean isDelete;
    private boolean isGetRoom;
    private Long roomId;
    private String roomName;
    public OrderResponse(Long id, String customerFullName, String customerPhoneNumber, String email, int numberGuest, Long price, Date startDateRent, Date stopDateRent, Date createdDate, boolean isDelete, boolean isGetRoom) {
        this.id = id;
        this.customerFullName = customerFullName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.email = email;
        this.numberGuest = numberGuest;
        this.price = price;
        this.startDateRent = startDateRent;
        this.stopDateRent = stopDateRent;
        this.createdDate = createdDate;
        this.isDelete = isDelete;
        this.isGetRoom = isGetRoom;
    }
}
