package vn.edu.hnue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hnue.backend.model.RoleName;
import vn.edu.hnue.backend.model.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, RoleName> {
    Optional<Role> findByName(RoleName roleName);
}
