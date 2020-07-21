package vn.edu.hnue.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.bean.ResponseSuccessBean;
import vn.edu.hnue.backend.model.dto.RoomDTO;
import vn.edu.hnue.backend.service.home.RoomService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/room")
@CrossOrigin("*")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<ResponseBean> create(@Valid @RequestBody List<RoomDTO> roomDTOS, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(new ResponseErrorBean("Value incorrect!"), HttpStatus.CONFLICT);
        }
        ResponseBean responseBean = roomService.create(roomDTOS);
        return new ResponseEntity<>(responseBean, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<ResponseBean> getAllHome() {
        return new ResponseEntity<>(roomService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<ResponseBean> findHome(@RequestParam("addressCity") String city, @RequestParam("startTime") String startTime,
                                                 @RequestParam("stopTime") String stopTime, @RequestParam("maxGuest") String numberGuest) {
        return new ResponseEntity<>(roomService.search(city,startTime, stopTime, numberGuest),HttpStatus.OK);
    }
    @PutMapping("/lock")
    public ResponseEntity<ResponseBean> lock(@RequestParam("id") Long id , @RequestParam("isLocked") Boolean isLocked) {
        return new ResponseEntity<>(roomService.lock(id,isLocked), HttpStatus.OK);
    }

}
