package vn.edu.hnue.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.hnue.backend.model.bean.ResponseBean;
import vn.edu.hnue.backend.model.bean.ResponseErrorBean;
import vn.edu.hnue.backend.model.dto.AccountDTO;
import vn.edu.hnue.backend.service.account.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<ResponseBean> getAllAccountOther() {
        return new ResponseEntity<>(accountService.getAllAccountOther(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBean> updateAccount(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(new ResponseErrorBean(409, "Some field not correct!"), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(accountService.updateAccount(accountDTO), HttpStatus.OK);
    }

    @PutMapping("/enable")
    public ResponseEntity<ResponseBean> lock(@RequestParam("id") Long id, @RequestParam("isEnable") Boolean isEnable) {
        return new ResponseEntity<>(accountService.enabled(id, isEnable), HttpStatus.OK);
    }

}
