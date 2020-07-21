package vn.edu.hnue.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phoneNumber;
    private Date birthDate;
    private String idCard;
    private String address;
    private String avatarUrl;
    private boolean isMale;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;
    @ManyToMany(mappedBy = "guest")
    private List<Room> roomsRent;

    public User(@NotBlank String fullName, String phoneNumber, Date birthDate, String idCard, String address, String avatarUrl, boolean isMale) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.idCard = idCard;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.isMale = isMale;
    }
}
