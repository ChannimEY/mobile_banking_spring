package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.*;

import java.util.List;

public interface AccountService {
    AccountResponse create(CreateAccountRequest request);
    List<AccountResponse> findAll();
    AccountResponse findByAccNum(String accNum);
    List<AccountResponse> findByCustomer(Integer customerId);
    void deleteByAccNum(String accNum);
    AccountResponse updateByAccNum(String accNum, UpdateAccountRequest request);
    void disableByAccNum(String accNum);
}
