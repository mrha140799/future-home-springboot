package vn.edu.hnue.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @NaturalId
    private String email;
    @NotBlank
    @Size(min = 6,max = 255)
    private String password;
    private boolean enable;
    private boolean isDelete;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, mappedBy = "account")
    private VerificationToken verificationToken;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Account() {
    }

    public Account(@NotBlank String email, @NotBlank @Size(min = 6, max = 255) String password, User user, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.enable = false;
        this.user = user;
    }
}
