package kh.edu.cstad.mbapi.controller;


import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private  final CustomerService customerService;
    @GetMapping("/{phoneNumber}")
    public CustomerResponse findByPhoneNumber(@PathVariable String phoneNumber){
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public  List<CustomerResponse> findAll(){
        return  customerService.findAll();
    }
    @PostMapping
    public CustomerResponse createNew(@RequestBody CreateCustomerRequest createCustomerRequest){
        return  customerService.createNew(createCustomerRequest);
    }

}
