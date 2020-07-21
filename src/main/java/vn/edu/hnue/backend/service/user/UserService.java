package vn.edu.hnue.backend.service.user;

import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.dto.UserDTO;

public interface UserService {
    ResponseBean register(UserDTO userDTO);
    ResponseBean confirmEmail(String jwt);
    ResponseBean login(UserDTO userDTO);
    ResponseBean me();
}
