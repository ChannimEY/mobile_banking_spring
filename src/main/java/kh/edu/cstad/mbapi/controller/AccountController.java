package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.dto.*;
import kh.edu.cstad.mbapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@RequestBody @Validated CreateAccountRequest request) {
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

    @GetMapping("/by-customer/{customerId}")
    public List<AccountResponse> findByCustomer(@PathVariable Integer customerId) {
        return accountService.findByCustomer(customerId);
    }

    @DeleteMapping("/{accNum}")
    public void deleteByAccNum(@PathVariable String accNum) {
        accountService.deleteByAccNum(accNum);
    }

    @PatchMapping("/{accNum}")
    public AccountResponse updateByAccNum(
            @PathVariable String accNum,
            @RequestBody @Validated UpdateAccountRequest request
    ) {
        return accountService.updateByAccNum(accNum, request);
    }

    @PutMapping("/{accNum}/disable")
    public void disableAccount(@PathVariable String accNum) {
        accountService.disableByAccNum(accNum);
    }
}
