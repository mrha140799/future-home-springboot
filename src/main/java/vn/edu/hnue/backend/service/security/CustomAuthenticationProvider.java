package vn.edu.hnue.backend.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.edu.hnue.backend.model.entity.Account;
import vn.edu.hnue.backend.model.entity.VerificationToken;
import vn.edu.hnue.backend.repository.AccountRepository;
import vn.edu.hnue.backend.repository.VerificationTokenRepository;
import vn.edu.hnue.backend.service.security.account.AccountPrinciple;
import vn.edu.hnue.backend.service.security.jwt.JwtProvider;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        if (!accountRepository.existsByEmail(username)) {
            throw new BadCredentialsException("Username not found!");
        }
        Account account = accountRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Username not found!"));
        if (account == null) {
            throw new BadCredentialsException("Account is empty!");
        }
        if (!encoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Password is incorrect!");
        }
        if (!account.isEnable()) {
            throw new BadCredentialsException("Account is not activated!");
        }
        if (account.isDelete()) {
            throw new BadCredentialsException("Account is deleted!");
        }
        String jwt = jwtProvider.generateTokenByUsername(username);
        VerificationToken verificationToken =account.getVerificationToken();
        verificationToken.setToken(jwt);
        verificationToken.setUpdatedDate(new Date());
        verificationTokenRepository.save(verificationToken);
        List<GrantedAuthority> authorities = account.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(AccountPrinciple.build(account), null, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}