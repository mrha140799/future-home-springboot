package vn.edu.hnue.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    @NotBlank
    private String fullName;
    @NotBlank
    private String phoneNumber;
    private String email;
    private String token;
    private Date createdDate;
    private Date updatedDate;
    private boolean isEnable;
    private boolean isDeleted;
    private List<String> roles;

    public AccountDTO(Long id, String fullName, String phoneNumber, String email, String token, Date createdDate, Date updatedDate, boolean isEnable, boolean isDeleted) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.token = token;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isEnable = isEnable;
        this.isDeleted = isDeleted;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        isEnable = enable;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
