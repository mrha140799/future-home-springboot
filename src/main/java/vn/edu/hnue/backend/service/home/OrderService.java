package vn.edu.hnue.backend.service.home;

import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.dto.OrderDTO;

public interface OrderService {
    ResponseBean createOrder(OrderDTO orderDTO);
    ResponseBean findAll();
    ResponseBean exceptRent(Long id);
}
