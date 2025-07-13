package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse updateByPhoneNumber(String phoneNumber);
    CustomerResponse findByPhoneNumber(String phoneNumber);
    List<CustomerResponse> findAll();
    CustomerResponse createNew(CreateCustomerRequest createCustomerRequest);


}
