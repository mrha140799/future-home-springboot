package vn.edu.hnue.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.dto.UserDTO;
import vn.edu.hnue.backend.model.util.Constants;
import vn.edu.hnue.backend.service.thread.AsyncService;
import vn.edu.hnue.backend.service.user.UserService;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AsyncService asyncService;

    @PostMapping("/register")
    public ResponseEntity<ResponseBean> register (@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws ExecutionException, InterruptedException {
        if (bindingResult.hasFieldErrors())
            return new ResponseEntity<>(new ResponseErrorBean("Value incorrect!"), HttpStatus.CONFLICT);
        ResponseBean responseBean = userService.register(userDTO);
        if (responseBean.getCode()==Constants.STATUS_CODE_1) asyncService.waitMilliSecondToSendEmail(userDTO,30000);
        return new ResponseEntity<>(responseBean, HttpStatus.CREATED);
    }
    @GetMapping("/me")
    public ResponseEntity<ResponseBean> me () {
        return new ResponseEntity<>(userService.me(),HttpStatus.OK);
    }

    @PutMapping("/confirm-email")
    public ResponseEntity<ResponseBean> confirmEmail(@RequestParam("token") String token) {
        return new ResponseEntity<>(userService.confirmEmail(token),HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseBean> login(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors())
            return new ResponseEntity<>(new ResponseErrorBean("Username or password inCorrect!"), HttpStatus.CONFLICT);
        ResponseBean responseBean = userService.login(userDTO);
        return new ResponseEntity<>(responseBean, HttpStatus.OK);

    }
}
