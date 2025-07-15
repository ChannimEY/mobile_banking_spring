package kh.edu.cstad.mbapi.service.Impl;

import kh.edu.cstad.mbapi.domain.*;
import kh.edu.cstad.mbapi.dto.*;
import kh.edu.cstad.mbapi.mapper.CustomerMapper;
import kh.edu.cstad.mbapi.repository.*;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;
    private final CustomerSegmentRepository segmentRepo;
    private final CustomerMapper customerMapper;
    private final KYCRepository kycRepo;

    @Override
    @Transactional
    public CustomerResponse createNew(CreateCustomerRequest request) {
        // Validate uniqueness
        if (customerRepo.existsByPhoneNumber(request.phoneNumber()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number already exists");
        if (customerRepo.existsByEmail(request.email()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        if (kycRepo.existsByNationalCardId(request.nationalCardId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "National card ID already exists");

        CustomerSegment segment = segmentRepo.findById(request.customerSegmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer segment not found"));

        Customer customer = customerMapper.fromCreateCustomerRequest(request);
        customer.setCustomerSegment(segment);
        customer.setIsDeleted(false);

        Customer savedCustomer = customerRepo.save(customer);


        KYC kyc = new KYC();
        kyc.setNationalCardId(request.nationalCardId());
        kyc.setCustomer(savedCustomer);
        kyc.setIsVerify(false);
        kyc.setIsDeleted(false);

        kycRepo.save(kyc);

        return customerMapper.toCustomerResponse(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest request) {
        Customer customer = customerRepo.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerMapper.toCustomerPartially(request, customer);

        return customerMapper.toCustomerResponse(customerRepo.save(customer));
    }

    @Override
    @Transactional
    public void disableByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepo.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customer.setIsDeleted(true);
        customerRepo.save(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepo.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> findAll() {
        return customerRepo.findAllByIsDeletedFalse().stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }
}
