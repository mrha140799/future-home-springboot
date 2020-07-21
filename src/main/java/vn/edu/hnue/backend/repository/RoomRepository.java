package vn.edu.hnue.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.hnue.backend.model.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByAddressCityAndMaxGuestGreaterThanEqualAndIsLockedFalse(String addressCity, int maxGuest);
    List<Room> findAllByIsDeletedFalse();
}
