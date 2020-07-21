package vn.edu.hnue.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.dto.OrderDTO;
import vn.edu.hnue.backend.service.home.OrderService;

@RestController
@RequestMapping("/v1/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public ResponseEntity<ResponseBean> createOder(@RequestBody OrderDTO orderDTO) {
        return new ResponseEntity<>(orderService.createOrder(orderDTO), HttpStatus.OK);
    }
    @GetMapping("/get-by-user")
    public ResponseEntity<ResponseBean> getAllByUser() {
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }
    @PostMapping("/except-rent")
    public ResponseEntity<ResponseBean> exceptRent(@RequestParam("id") Long id) {
        return new ResponseEntity<>(orderService.exceptRent(id), HttpStatus.OK);
    }
}
