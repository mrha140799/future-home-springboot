package vn.edu.hnue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hnue.backend.model.entity.Account;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);
    Boolean existsByEmail(String username);
    LinkedList<Account> findByEnable(boolean isEnable);
    List<Account> findAllByEmailNot(String email);
}
