package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccNum(String accNum);
    List<Account> findByCustomer(Customer customer);
}
