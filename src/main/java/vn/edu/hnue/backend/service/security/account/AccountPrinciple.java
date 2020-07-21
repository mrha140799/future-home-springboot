package vn.edu.hnue.backend.service.security.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.hnue.backend.model.entity.Account;
import vn.edu.hnue.backend.model.entity.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String fullName;
    private Date birthDate;
    private String avatarUrl;
    private String email;
    private String token;
    private Long expiredTime;
    private List<String> roles;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static AccountPrinciple build(Account account) {
        Date now = new Date();
        User user = account.getUser();
        List<GrantedAuthority> authorities = account.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new AccountPrinciple(
                user.getId(), user.getFullName(), user.getBirthDate(), user.getAvatarUrl(),
                account.getEmail(), account.getVerificationToken().getToken(), ((now.getTime() + account.getVerificationToken().getExpiredTime()) - now.getTime()),
                account.getPassword(), authorities);
    }

    private AccountPrinciple(Long id, String fullName, Date birthDate, String avatarUrl, String email, String token, Long expiredTime, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
        this.email = email;
        this.token = token;
        this.expiredTime = expiredTime;
        this.password = password;
        this.authorities = authorities;
        this.roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
