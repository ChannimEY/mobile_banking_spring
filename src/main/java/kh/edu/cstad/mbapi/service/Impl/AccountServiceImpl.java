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
import kh.edu.cstad.mbapi.util.CurrencyUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;



    @Override
    public AccountResponse create(CreateAccountRequest createAccountRequest) {

        Account account = new Account();

        // Validate account type
        AccountType accountType = accountTypeRepository
                .findByType(createAccountRequest.accountType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account Type Not Found"));

        // Validation customer phone number
        Customer customer = customerRepo
                .findByPhoneNumber(createAccountRequest.phoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Customer phone number not found"));

        switch (createAccountRequest.actCurrency()) {
            case CurrencyUtil.USD -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(10)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 10 Dollars");
                }
                // Set over limit base on customer segment
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000));
                }
            }
            case CurrencyUtil.KHR -> {
                if (createAccountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 40,000 KHR");
                }
                // Set over limit base on customer segment
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000 * 4000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Currency is not supported");
        }

        // Validate account no
        if (createAccountRequest.accNum() != null) {
            if (accountRepo.existsByAccNum(createAccountRequest.accNum())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Account with Act No %s already exists", createAccountRequest.accNum()));
            }
            account.setAccNum(createAccountRequest.accNum());
        } else {
            String accNum;
            do {
                accNum = String.format("%09d", new Random().nextInt(1_000_000_000));
            } while (accountRepo.existsByAccNum(accNum));
            account.setAccNum(accNum);
        }

        // Set data logic
        account.setAccName(createAccountRequest.accName());
        account.setActCurrency(createAccountRequest.actCurrency().name());
        account.setBalance(createAccountRequest.balance());
        account.setIsHide(false);
        account.setIsDeleted(false);
        account.setCustomer(customer);
        account.setAccountType(accountType);

        account = accountRepo.save(account);

        return accountMapper.toAccountResponse(account);
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
