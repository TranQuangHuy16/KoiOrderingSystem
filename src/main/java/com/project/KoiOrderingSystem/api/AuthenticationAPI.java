package com.project.KoiOrderingSystem.api;

import com.project.KoiOrderingSystem.entity.Account;
import com.project.KoiOrderingSystem.model.AccountResponse;
import com.project.KoiOrderingSystem.model.LoginRequest;
import com.project.KoiOrderingSystem.model.ProfileRequest;
import com.project.KoiOrderingSystem.model.RegisterRequest;
import com.project.KoiOrderingSystem.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@SecurityRequirement(name = "api")

public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest) {
        AccountResponse newAccount = authenticationService.register(registerRequest);
        return ResponseEntity.ok(newAccount);

    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        AccountResponse account = authenticationService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/account")
    public ResponseEntity getAllAccount() {
        List<AccountResponse> accounts = authenticationService.getAllAccount();
        return ResponseEntity.ok(accounts);
    }

    @PutMapping("{accountId}")
    public ResponseEntity update(@PathVariable long accountId, @Valid @RequestBody ProfileRequest profileRequest){
        AccountResponse updateAccount = authenticationService.update(accountId, profileRequest);
        return ResponseEntity.ok(updateAccount);
    }
}
