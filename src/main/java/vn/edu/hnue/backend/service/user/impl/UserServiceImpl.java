package vn.edu.hnue.backend.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hnue.backend.model.RoleName;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.bean.ResponseSuccessBean;
import vn.edu.hnue.backend.model.dto.UserDTO;
import vn.edu.hnue.backend.model.dto.UserPrincipleDTO;
import vn.edu.hnue.backend.model.entity.Account;
import vn.edu.hnue.backend.model.entity.Role;
import vn.edu.hnue.backend.model.entity.User;
import vn.edu.hnue.backend.model.entity.VerificationToken;
import vn.edu.hnue.backend.model.util.Constants;
import vn.edu.hnue.backend.model.util.MessageUtil;
import vn.edu.hnue.backend.repository.AccountRepository;
import vn.edu.hnue.backend.repository.RoleRepository;
import vn.edu.hnue.backend.repository.UserRepository;
import vn.edu.hnue.backend.repository.VerificationTokenRepository;
import vn.edu.hnue.backend.service.security.CustomAuthenticationProvider;
import vn.edu.hnue.backend.service.security.account.AccountPrinciple;
import vn.edu.hnue.backend.service.security.jwt.JwtProvider;
import vn.edu.hnue.backend.service.user.UserService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Override
    public ResponseBean register(UserDTO userDTO) {
        try {
            if (accountRepository.existsByEmail(userDTO.getEmail()))
                return new ResponseErrorBean(Constants.STATUS_CODE_409, "Email đã tồn tại");
            Set<Role> roles = new HashSet<>();
            Set<String> strRoles = userDTO.getRoles();
            for (String role : strRoles) {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN).get();
                        roles.add(adminRole);
                        break;
                    default:
                        Role teacherRole = roleRepository.findByName(RoleName.ROLE_USER).get();
                        roles.add(teacherRole);
                        break;
                }
            }
            User user = new User(userDTO.getFullName(), userDTO.getPhoneNumber(), null, null, null, null, true);
            Account account = new Account(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()), user, roles);
            String jwt = jwtProvider.generateTokenByUsername(userDTO.getEmail());
            VerificationToken verificationToken = new VerificationToken(jwt, account);
            verificationTokenRepository.save(verificationToken);
            return new ResponseSuccessBean(user.getId());
        } catch (Exception e) {
            return new ResponseErrorBean("Lỗi: " + e);
        }
    }

    @Override
    public ResponseBean confirmEmail(String jwt) {
        if (!jwtProvider.validateJwtToken(jwt) || jwt == null)
            return new ResponseErrorBean(messageUtil.getMessageDefaultEmpty("error.token.valid"));
        try {
            String email = jwtProvider.getUserNameFromJwtToken(jwt);
            Account account = accountRepository.findByEmail(email).get();
            account.setEnable(true);
            accountRepository.save(account);
            return new ResponseSuccessBean(account.getId());
        } catch (Exception e) {
            return new ResponseErrorBean(e.getMessage());
        }
    }

    @Override
    public ResponseBean login(UserDTO userDTO) {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccountPrinciple accountPrinciple = (AccountPrinciple)authentication.getPrincipal();
        return new ResponseSuccessBean(accountPrinciple);
    }

    @Override
    public ResponseBean me() {
        AccountPrinciple accountPrinciple = (AccountPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseSuccessBean(accountPrinciple);
    }

}
