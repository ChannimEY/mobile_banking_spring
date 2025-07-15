package kh.edu.cstad.mbapi.service.Impl;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.domain.CustomerSegment;
import kh.edu.cstad.mbapi.domain.KYC;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbapi.mapper.CustomerMapper;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.repository.KYCRepository;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private  final CustomerMapper customerMapper;
    private final KYCRepository kycRepository;
    private final CustomerSegmentRepository customerSegmentRepository;

    @Override
    @Transactional
    public CustomerResponse createNew(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }
        if (kycRepository.existsByNationalCardId(request.nationalCardId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "National ID already exists");
        }

        Customer customer = customerMapper.fromCreateCustomerRequest(request);
        customer.setIsDeleted(false);

        CustomerSegment segment = customerSegmentRepository.findById(request.customerSegmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Segment not found"));
        customer.setCustomerSegment(segment);

        customer = customerRepository.save(customer);

        KYC kyc = new KYC();
        kyc.setCustomer(customer);
        kyc.setNationalCardId(request.nationalCardId());
        kyc.setIsDeleted(false);
        kyc.setIsVerify(false);
        kycRepository.save(kyc);

        return customerMapper.toCustomerResponse(customer);
    }


    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {
        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer =customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer phone number not found"
                        ));
        customerMapper.toCustomerPartially(
                updateCustomerRequest,
                customer
        );

        customer=customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository
                .findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(
                        ()->new ResponseStatusException(HttpStatus.NOT_FOUND)
                );
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers=customerRepository.findAllByIsDeletedFalse();
        return customers.stream()
                .map(customerMapper::toCustomerResponse)
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

        Customer customer = customerMapper.fromCreateCustomerRequest(createCustomerRequest);
        customer.setIsDeleted(false);

        log.info("Customer Id before save : {}", customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer id after save: {}", customer.getId());

        return customerMapper.toCustomerResponse(customer);
    }
}
