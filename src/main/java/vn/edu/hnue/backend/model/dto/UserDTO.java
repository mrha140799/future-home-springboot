package vn.edu.hnue.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private String phoneNumber;
    @NotBlank
    private String email;
    @NotBlank
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private Set<String> roles;

}
