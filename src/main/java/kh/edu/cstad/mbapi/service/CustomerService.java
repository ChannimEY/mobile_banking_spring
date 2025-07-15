package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
    CustomerResponse createNew(CreateCustomerRequest request);
    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest request);
    void disableByPhoneNumber(String phoneNumber);
    CustomerResponse findByPhoneNumber(String phoneNumber);
    List<CustomerResponse> findAll();
}