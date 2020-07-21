package vn.edu.hnue.backend.service.home.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.bean.ResponseSuccessBean;
import vn.edu.hnue.backend.model.dto.OrderDTO;
import vn.edu.hnue.backend.model.dto.OrderResponse;
import vn.edu.hnue.backend.model.entity.Account;
import vn.edu.hnue.backend.model.entity.Order;
import vn.edu.hnue.backend.model.entity.Room;
import vn.edu.hnue.backend.repository.AccountRepository;
import vn.edu.hnue.backend.repository.OrderRepository;
import vn.edu.hnue.backend.repository.RoomRepository;
import vn.edu.hnue.backend.service.home.OrderService;
import vn.edu.hnue.backend.service.security.account.AccountPrinciple;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Override
    public ResponseBean createOrder(OrderDTO orderDTO) {
        try {
            Order order = convertToOrder(orderDTO);
            orderRepository.save(order);
            return new ResponseSuccessBean(order.getId());
        } catch (Exception e) {
            return new ResponseErrorBean(e);
        }
    }

    @Override
    public ResponseBean findAll() {
        AccountPrinciple accountPrinciple = (AccountPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Order> orders = orderRepository.findAllByRoomRent_CreatedUser_Account_EmailAndIsDeleteIsFalseAndIsGetRoomIsFalse(accountPrinciple.getEmail());
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order: orders) {
            orderResponses.add(convertToOrderResponse(order));
        }
        return new ResponseSuccessBean(orderResponses);
    }

    @Override
    public ResponseBean exceptRent(Long id) {
        try {
            Order order = orderRepository.findById(id).get();
            order.setGetRoom(true);
            orderRepository.save(order);
            return new ResponseSuccessBean(order.getId());
        } catch (Exception e) {
            return new ResponseErrorBean("error: "+ e);
        }
    }

    private OrderResponse convertToOrderResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse(order.getId(), order.getCustomerFullName(), order.getCustomerPhoneNumber(), order.getEmail(), order.getNumberGuest(), order.getPrice(), order.getStartDateRent(),
                order.getStopDateRent(),order.getCreatedDate(),order.isDelete(), order.isGetRoom());
        orderResponse.setRoomId(order.getRoomRent().getId());
        orderResponse.setRoomName(order.getRoomRent().getRoomName());
        return orderResponse;
    }

    private Order convertToOrder(OrderDTO orderDTO) throws ParseException {
        Order order = new Order();
        AccountPrinciple accountPrinciple = (AccountPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByEmail(accountPrinciple.getEmail()).get();
        order.setCustomerAccount(account);
        Room room = roomRepository.findById(orderDTO.getRoomRentId()).get();
        order.setRoomRent(room);
        order.setCustomerFullName(orderDTO.getCustomerFullName());
        order.setCustomerPhoneNumber(orderDTO.getCustomerPhoneNumber());
        order.setEmail(orderDTO.getEmail());
        order.setNumberGuest(orderDTO.getNumberGuest());
        order.setPrice(orderDTO.getPrice());
        order.setStartDateRent(orderDTO.getStartDateRent());
        order.setStopDateRent(orderDTO.getStartDateRent());
        return order;
    }

}
