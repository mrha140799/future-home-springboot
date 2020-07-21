package vn.edu.hnue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hnue.backend.model.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByRoomRent_CreatedUser_Account_EmailAndIsDeleteIsFalseAndIsGetRoomIsFalse(String email);
}
