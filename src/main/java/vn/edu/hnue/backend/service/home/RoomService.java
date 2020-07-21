package vn.edu.hnue.backend.service.home;

import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    ResponseBean create(List<RoomDTO> roomDTOS);
    ResponseBean getAll();
    ResponseBean lock(Long id, boolean isLocked);
    ResponseBean search(String city, String startTime, String stopTime, String maxGuest);
}
