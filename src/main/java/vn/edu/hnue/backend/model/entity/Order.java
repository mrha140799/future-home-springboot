package vn.edu.hnue.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name = "orders")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Account customerAccount;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room roomRent;
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

    public Order() {
        this.isDelete = false;
        this.isGetRoom = false;
        this.createdDate = new Date();
    }
}
