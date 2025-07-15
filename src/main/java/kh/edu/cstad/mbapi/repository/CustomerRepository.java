package kh.edu.cstad.mbapi.repository;

import kh.edu.cstad.mbapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByIsDeletedFalse();
boolean existsByPhoneNumber(String phoneNumber);
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.isDeleted = true WHERE c.phoneNumber = ?1")
    void disableByPhoneNumber(String phoneNumber);

    Optional<Customer> findByPhoneNumberAndIsDeletedFalse(String phoneNumber);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

}
