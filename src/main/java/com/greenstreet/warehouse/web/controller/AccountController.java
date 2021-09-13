package com.greenstreet.warehouse.web.controller;


import com.greenstreet.warehouse.model.request.PasswordChangeDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ResponseUserDTO> getAccount() {
        return ResponseEntity.ok().body(accountService.getUserWithAuthorities());
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
         return ResponseEntity.ok().body(accountService.changePassword(passwordChangeDTO));
    }
}
