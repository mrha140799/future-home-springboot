package vn.edu.hnue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hnue.backend.model.entity.Home;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {
    List<Home> findAllByIsDeletedFalse();
}
