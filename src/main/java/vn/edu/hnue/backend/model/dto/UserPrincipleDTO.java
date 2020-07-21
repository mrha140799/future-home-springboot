package vn.edu.hnue.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserPrincipleDTO {
    private String fullName;
    private String avatarUrl;
    private String email;
    private String token;
    private long expiredTime;
    private List<String> roles = new ArrayList<>();

    public UserPrincipleDTO(String fullName, String avatarUrl ,String email, String token, long expiredTime, Collection<? extends GrantedAuthority> authorities) {
        this.avatarUrl = avatarUrl;
        this.fullName =fullName;
        this.email = email;
        this.token = token;
        this.expiredTime = expiredTime;
        for (GrantedAuthority authority : authorities) {
            this.roles.add(authority.getAuthority());
        }

    }
}
