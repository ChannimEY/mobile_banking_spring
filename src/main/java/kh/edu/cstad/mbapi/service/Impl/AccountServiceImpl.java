package kh.edu.cstad.mbapi.service.Impl;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.domain.AccountType;
import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.*;
import kh.edu.cstad.mbapi.mapper.AccountMapper;
import kh.edu.cstad.mbapi.repository.AccountRepository;
import kh.edu.cstad.mbapi.repository.AccountTypeRepository;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;

    @Override
    public AccountResponse create(CreateAccountRequest request) {
        Customer customer = customerRepo.findById(request.customerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        AccountType accountType = accountTypeRepository.findById(request.accountTypeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account type not found"));

        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account.setAccNum(generateAccountNumber());
        account.setIsDeleted(false);


        String segment = customer.getCustomerSegment().getSegment().toUpperCase();
        account.setOverLimit(getLimitBySegment(segment));

        return accountMapper.toAccountResponse(accountRepo.save(account));
    }

    private String generateAccountNumber() {
        return "ACC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private BigDecimal getLimitBySegment(String segment) {
        return switch (segment) {
            case "GOLD" -> BigDecimal.valueOf(50000);
            case "SILVER" -> BigDecimal.valueOf(10000);
            default -> BigDecimal.valueOf(5000);
        };
    }

    @Override
    public List<AccountResponse> findAll() {
        return accountRepo.findAll().stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse findByAccNum(String accNum) {
        return accountRepo.findByAccNum(accNum)
                .map(accountMapper::toAccountResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "find account by account number not found"));
    }

    @Override
    public List<AccountResponse> findByCustomer(Integer customerId) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "find account by Customer not found"));
        return accountRepo.findByCustomer(customer)
                .stream().map(accountMapper::toAccountResponse).toList();
    }

    @Override
    public void deleteByAccNum(String accNum) {
        Account account = accountRepo.findByAccNum(accNum)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "delete account by account number not  found"));
        accountRepo.delete(account);
    }

    @Override
    public AccountResponse updateByAccNum(String accNum, UpdateAccountRequest request) {
        Account account = accountRepo.findByAccNum(accNum)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "update account by account number not found"));



        accountMapper.toCustomerPartially(request, account);
        return accountMapper.toAccountResponse(accountRepo.save(account));
    }

    @Override
    public void disableByAccNum(String accNum) {
        Account account = accountRepo.findByAccNum(accNum)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Disable account by account number not found"));
        account.setIsDeleted(true);
        accountRepo.save(account);
    }
}
