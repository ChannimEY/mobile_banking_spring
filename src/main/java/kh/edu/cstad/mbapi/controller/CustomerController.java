package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.dto.*;
import kh.edu.cstad.mbapi.service.CustomerService;
import kh.edu.cstad.mbapi.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;
    private final KYCService kycService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Validated CreateCustomerRequest request) {
        return customerService.createNew(request);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{phoneNumber}")
    public CustomerResponse findByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateByPhoneNumber(@PathVariable String phoneNumber,
                                                @RequestBody UpdateCustomerRequest request) {
        return customerService.updateByPhoneNumber(phoneNumber, request);
    }

    @DeleteMapping("/{phoneNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableByPhoneNumber(@PathVariable String phoneNumber) {
        customerService.disableByPhoneNumber(phoneNumber);
    }

}
