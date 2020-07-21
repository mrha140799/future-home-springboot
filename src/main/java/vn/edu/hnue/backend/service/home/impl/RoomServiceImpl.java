package vn.edu.hnue.backend.service.home.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.bean.ResponseSuccessBean;
import vn.edu.hnue.backend.model.dto.HomeDTO;
import vn.edu.hnue.backend.model.dto.RoomDTO;
import vn.edu.hnue.backend.model.entity.Home;
import vn.edu.hnue.backend.model.entity.Room;
import vn.edu.hnue.backend.model.entity.User;
import vn.edu.hnue.backend.repository.HomeRepository;
import vn.edu.hnue.backend.repository.RoomRepository;
import vn.edu.hnue.backend.repository.UserRepository;
import vn.edu.hnue.backend.service.home.RoomService;
import vn.edu.hnue.backend.service.security.account.AccountPrinciple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public ResponseBean create(List<RoomDTO> roomDTOS) {
        try {
            List<Room> rooms = convertToListRoom(roomDTOS);
            roomRepository.saveAll(rooms);
            return new ResponseSuccessBean(rooms);
        }catch (Exception e) {
            return new ResponseErrorBean("Error: "+e);
        }
    }

    @Override
    public ResponseBean getAll() {
        List<Room> rooms = roomRepository.findAllByIsDeletedFalse();
        List<RoomDTO> roomDTOS = adminConvertToListRoomDTO(rooms);
        return new ResponseSuccessBean(roomDTOS);
    }

    @Override
    public ResponseBean lock(Long id, boolean isLocked) {
        try {
            Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException(new UsernameNotFoundException("Home not found!")));
            room.setLocked(isLocked);
            roomRepository.save(room);
            return new ResponseSuccessBean(id);
        }catch (Exception e) {
            return new ResponseErrorBean("Error: "+e);
        }
    }

    @Override
    public ResponseBean search(String city, String startTime, String stopTime, String maxGuest) {
        try {
            int maxGuestInt = Integer.parseInt(maxGuest);
            List<Room> rooms = roomRepository.findAllByAddressCityAndMaxGuestGreaterThanEqualAndIsLockedFalse(city,maxGuestInt);
            List<RoomDTO> roomDTOS = convertToListRoomDTO(rooms);
            return new ResponseSuccessBean(roomDTOS);
        } catch (Exception e) {
            return new ResponseErrorBean("error: "+e.getMessage());
        }
    }


    private Home convertToHome(HomeDTO homeDTO) {
        return new Home(homeDTO.getHomeName(), homeDTO.getAddressCountry(), homeDTO.getAddressCity(),
                homeDTO.getAddressDistrict(), homeDTO.getAddressCommunes(), homeDTO.getAddressDetail(),
                homeDTO.getNumberBathRoom(), homeDTO.getDescription(), homeDTO.getMaxGuest(),
                homeDTO.getCurrentGuest(), homeDTO.getPrice(), homeDTO.getHomeArea(),
                homeDTO.getCreatedDate(), homeDTO.getUpdatedDate());
    }

    private HomeDTO convertToHomeDTO(Home home) {
        return new HomeDTO(home.getId(), home.getHomeName(),
                home.getAddressCountry(), home.getAddressCity(), home.getAddressDistrict(),
                home.getAddressCommunes(), home.getAddressDetail(), home.getNumberBathRoom(),
                home.getDescription(), home.getMaxGuest(), home.getCurrentGuest(), home.getPrice(),
                home.getHomeArea(), home.getCreatedDate(), home.getUpdatedDate(), home.isLocked(), home.isDeleted(), home.isChecked());
    }
    private List<Room> convertToListRoom(List<RoomDTO> roomDTOS) {
        List<Room> rooms = new ArrayList<>();
        AccountPrinciple accountPrinciple = (AccountPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByAccount_Email(accountPrinciple.getUsername()).get();
        for (RoomDTO roomDTO : roomDTOS) {
            StringBuilder imageIds = new StringBuilder();
            List<String> imagesId =roomDTO.getImages();
            for (int i=0; i < imagesId.size(); i++) {
                imageIds.append(imagesId.get(i));
                if (i < imagesId.size()-1) {
                    imageIds.append(",");
                }
            }
            rooms.add(new Room(roomDTO.getAddressCountry(), roomDTO.getAddressCity(),
                    roomDTO.getAddressDistrict(), roomDTO.getAddressCommunes(),
                    roomDTO.getAddressDetail(),roomDTO.getRoomName(),
                    roomDTO.getRoomArea(),roomDTO.getMaxGuest(),roomDTO.getNumberBathRoom(),
                    roomDTO.getDescription(),roomDTO.getPrice(), imageIds.toString(), user));
        }
        return  rooms;
    }
    private List<RoomDTO> convertToListRoomDTO(List<Room> rooms) {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOS.add(new RoomDTO(room.getId(), room.getAddressCountry(), room.getAddressCity(),
                    room.getAddressDistrict(), room.getAddressCommunes(), room.getAddressDetail(),
                    room.getRoomName(), room.getRoomArea(), room.getMaxGuest(), room.getNumberBathRoom(),
                    room.getDescription(), room.getPrice(), new ArrayList<String>(Arrays.asList(room.getImagesID().split(",")))));
        }
        return roomDTOS;
    }
    private List<RoomDTO> adminConvertToListRoomDTO(List<Room> rooms) {
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOS.add(new RoomDTO(room.getId(), room.getAddressCountry(), room.getAddressCity(),
                    room.getAddressDistrict(), room.getAddressCommunes(), room.getAddressDetail(),
                    room.getRoomName(), room.getRoomArea(), room.getMaxGuest(), room.getNumberBathRoom(),
                    room.getDescription(), room.getPrice(), room.isLocked(), room.isDeleted(), new ArrayList<String>(Arrays.asList(room.getImagesID().split(" ")))));
        }
        return roomDTOS;
    }
}
