package vn.edu.hnue.backend.service.security.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hnue.backend.model.entity.Account;
import vn.edu.hnue.backend.repository.AccountRepository;

@Service
public class AccountDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("USESR NOT FOUNT -> USERNAME OR EMAIL: " + email));
        return AccountPrinciple.build(account);
    }
}
