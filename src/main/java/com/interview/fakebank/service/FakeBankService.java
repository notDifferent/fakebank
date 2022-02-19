package com.interview.fakebank.service;

import com.interview.fakebank.dto.createAccount.BankAccountRequestDTO;
import com.interview.fakebank.dto.createAccount.BankAccountResponseDTO;
import com.interview.fakebank.dto.fetchBalance.FetchBalanceResponseDTO;
import com.interview.fakebank.dto.transfer.BankTransferRequestDTO;
import com.interview.fakebank.dto.transfer.BankTransferResponseDTO;
import com.interview.fakebank.dto.transferHistory.TransferHistoryResponseDTO;

public interface FakeBankService {

    BankAccountResponseDTO createBankAccount(BankAccountRequestDTO bankAccountRequestDTO);
    BankTransferResponseDTO transferAmount(BankTransferRequestDTO bankTransferRequestDTO);
    FetchBalanceResponseDTO fetchBalance(Long customerId, Long accountNumber);
    TransferHistoryResponseDTO fetchTransferHistory(Long customerId, Long accountNumber);

}
