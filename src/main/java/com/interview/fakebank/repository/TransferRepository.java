package com.interview.fakebank.repository;

import com.interview.fakebank.model.BankTransfer;
import com.interview.fakebank.model.ErrorMessage;
import com.interview.fakebank.model.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransferRepository extends JpaRepository<BankTransfer, Integer> {

    List<BankTransfer> findByDebitAccountOrCreditAccount(Long debitAccount, Long creditAccount);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE BankTransfer bt SET bt.status = (:status), bt.message = (:message) WHERE bt.id = (:id)")
    int updateTransferStatusAndMessageById(Long id, TransferStatus status, ErrorMessage message);

}
