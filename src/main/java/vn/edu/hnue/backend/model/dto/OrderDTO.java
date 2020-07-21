package vn.edu.hnue.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Long roomRentId;
    private String customerPhoneNumber;
    private String customerFullName;
    private String email;
    private int numberGuest;
    private Long price;
    private Date startDateRent;
    private Date stopDateRent;
}
