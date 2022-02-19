package com.interview.fakebank.repository;

import com.interview.fakebank.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AccountRepository extends JpaRepository<BankAccount, Integer> {

    BankAccount findByCustomerIdAndAccountNumber(Long customerId, Long accountNumber);

    BankAccount findByAccountNumber(Long accountNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BankAccount ba SET ba.currentBalance = (:currentBalance) WHERE ba.accountNumber = (:accountNumber)")
    int updateCurrentBalance(Long accountNumber, Long currentBalance);


}
