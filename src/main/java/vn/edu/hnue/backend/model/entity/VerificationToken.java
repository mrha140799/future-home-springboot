package vn.edu.hnue.backend.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date createdDate;
    private Date updatedDate;
    private boolean isDelete;
    private long expiredTime;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    public void setToken(String token) {
        this.createdDate = new Date();
        this.token = token;
    }

    public VerificationToken(String token, Account account) {
        this.token = token;
        this.account = account;
        this.createdDate = new Date();
        this.expiredTime = 86400*1000;
    }
}
