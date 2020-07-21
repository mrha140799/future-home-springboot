package vn.edu.hnue.backend.service.account.impl;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hnue.backend.model.RoleName;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.bean.ResponseSuccessBean;
import vn.edu.hnue.backend.model.dto.AccountDTO;
import vn.edu.hnue.backend.model.entity.Account;
import vn.edu.hnue.backend.model.entity.Role;
import vn.edu.hnue.backend.repository.AccountRepository;
import vn.edu.hnue.backend.service.account.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseBean getAllAccountOther() {
        List<Account> accounts = accountRepository.findAllByEmailNot(SecurityContextHolder.getContext().getAuthentication().getName());
        List<AccountDTO> accountDTOS = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOS.add(convertToAccountDTO(account));
        }
        return new ResponseSuccessBean(accountDTOS);
    }

    @Override
    public ResponseBean updateAccount(AccountDTO accountDTO) {
        Account account = accountRepository.findByEmail(accountDTO.getEmail()).orElseThrow( () -> new UsernameNotFoundException("Account not found!"));
        if (account == null || !account.getEmail().equals(accountDTO.getEmail())) {
            return new ResponseErrorBean("Not found account!");
        }
        account.getUser().setFullName(accountDTO.getFullName());
        account.getUser().setPhoneNumber(accountDTO.getPhoneNumber());
        account.setEnable(accountDTO.getIsEnable());
        account.setDelete(accountDTO.getIsDeleted());
        try {
            accountRepository.save(account);
            return new ResponseSuccessBean(convertToAccountDTO(account));
        }catch (Exception e) {
            return  new ResponseErrorBean("Updated fail!");
        }
    }

    @Override
    public ResponseBean enabled(Long id, boolean isEnable) {
       try {
           Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException(new UsernameNotFoundException("Account not found!")));
           account.setEnable(isEnable);
           accountRepository.save(account);
           return new ResponseSuccessBean(id);
       }catch (Exception e) {
           return new ResponseErrorBean("Error: "+e);
       }
    }

    private AccountDTO convertToAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO(account.getId(), account.getUser().getFullName(),
                account.getUser().getPhoneNumber(), account.getEmail(),
                account.getVerificationToken().getToken(), account.getVerificationToken().getCreatedDate(),
                account.getVerificationToken().getUpdatedDate(), account.isEnable(), account.isDelete());
        Set<Role> roles = account.getRoles();
        List<String> roleNames = new ArrayList<>();
        for (Role role : roles) {
            roleNames.add(role.getName().toString());
        }
        accountDTO.setRoles(roleNames);
        return accountDTO;
    }

}
