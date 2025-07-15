package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.dto.*;
import kh.edu.cstad.mbapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestBody @Validated CreateAccountRequest request) {
        return accountService.create(request);
    }

    @GetMapping
    public List<AccountResponse> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{accNum}")
    public AccountResponse findByAccNum(@PathVariable String accNum) {
        return accountService.findByAccNum(accNum);
    }

    @GetMapping("/customer/{customerId}")
    public List<AccountResponse> findByCustomer(@PathVariable Integer customerId) {
        return accountService.findByCustomer(customerId);
    }

    @DeleteMapping("/{accNum}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByAccNum(@PathVariable String accNum) {
        accountService.deleteByAccNum(accNum);
    }

    @PatchMapping("/{accNum}")
    public AccountResponse updateByAccNum(@PathVariable String accNum, @RequestBody UpdateAccountRequest request) {
        return accountService.updateByAccNum(accNum, request);
    }

    @PatchMapping("/{accNum}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableByAccNum(@PathVariable String accNum) {
        accountService.disableByAccNum(accNum);
    }
}
