package vn.edu.hnue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hnue.backend.model.entity.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    boolean existsByAccount_Email(String email);
    VerificationToken findByToken(String token);
    VerificationToken findByAccount_Email(String email);
}
