package kh.edu.cstad.mbapi.service.Impl;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository
                .findByPhoneNumber(phoneNumber)
                .map(customer -> CustomerResponse.builder()
                        .fullName(customer.getFullname())
                        .gender(customer.getGender())
                        .email(customer.getEmail())
                        .build())
                .orElseThrow(
                        ()->new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers=customerRepository.findAll();
        return customers.stream()
                .map(customer ->
                        CustomerResponse.builder()
                                .fullName(customer.getFullname())
                                .gender(customer.getGender())
                                .email(customer.getEmail())
                                .build())
                .toList();


    }

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "email already exist"
            );
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "phone number already exist"
            );
        }

        Customer customer = new Customer();
        customer.setFullname(createCustomerRequest.fullname());
        customer.setGender(createCustomerRequest.gender());
        customer.setEmail(createCustomerRequest.email());
        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
        customer.setRemark(createCustomerRequest.remark());
        customer.setIsDelete(false);

        log.info("Customer Id before save : {}", customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer id after save: {}", customer.getId());

        return CustomerResponse.builder()
                .fullName(customer.getFullname())
                .gender(customer.getGender())
                .email(customer.getEmail())
                .build();
    }
}
