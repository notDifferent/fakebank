package com.interview.fakebank.service;

import com.google.gson.Gson;
import com.interview.fakebank.dto.transfer.BankTransferResponseDTO;
import com.interview.fakebank.dto.transferHistory.TransferHistoryResponseDTO;
import com.interview.fakebank.model.BankAccount;
import com.interview.fakebank.model.BankTransfer;
import com.interview.fakebank.model.TransferStatus;
import com.interview.fakebank.repository.AccountRepository;
import com.interview.fakebank.repository.TransferRepository;
import com.interview.fakebank.service.impl.FakeBankServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.interview.fakebank.factory.ObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FakeBankServiceTest {

    @InjectMocks
    private FakeBankServiceImpl fakeBankService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransferRepository transferRepository;


    Gson gson = new Gson();

    @Test
    public void testCreateAccount() {
        when(accountRepository.save(any(BankAccount.class))).thenReturn(getCreditBankAccount());
        Long actualBankAccountNumber = fakeBankService.createBankAccount(getBankAccountDTO()).getAccountNumber();
        assertThat(actualBankAccountNumber).isEqualTo(creditBankAccountNumber);
    }

    @Test
    public void testTransfer() {
        when(transferRepository.save(any(BankTransfer.class))).thenReturn(getBankTransfer());
        when(accountRepository.findByCustomerIdAndAccountNumber(customerId,debitBankAccountNumber)).thenReturn(getDebitBankAccount());
        when(accountRepository.findByAccountNumber(creditBankAccountNumber)).thenReturn(getCreditBankAccount());
        when(accountRepository.updateCurrentBalance(debitBankAccountNumber,openingBalance-transferAmount)).thenReturn(1);
        when(accountRepository.updateCurrentBalance(creditBankAccountNumber,openingBalance+transferAmount)).thenReturn(2);
        when(transferRepository.updateTransferStatusAndMessageById(bankTransferId, TransferStatus.SUCCESSFUL, null)).thenReturn(3);
        BankTransferResponseDTO actualBankTransferResponseDTO = fakeBankService.transferAmount(getBankTransferRequestDTO());
        assertThat(gson.toJson(actualBankTransferResponseDTO)).isEqualTo(gson.toJson(getBankTransferResponseDTO()));
    }


    @Test
    public void testFetchBalance() {
        when(accountRepository.findByCustomerIdAndAccountNumber(customerId,debitBankAccountNumber)).thenReturn(getDebitBankAccount());
        Long actualBalance = fakeBankService.fetchBalance(customerId,debitBankAccountNumber).getCurrentBalance();
        assertThat(actualBalance).isEqualTo(openingBalance);
    }

    @Test
    public void testFetchTransferHistory() {
        when(accountRepository.findByCustomerIdAndAccountNumber(customerId,debitBankAccountNumber)).thenReturn(getDebitBankAccountAfterTransfer());
        when(transferRepository.findByDebitAccountOrCreditAccount(debitBankAccountNumber, debitBankAccountNumber)).thenReturn(getBankTransferListWithSuccessStatus());
        TransferHistoryResponseDTO actualTransferHistoryResponseDTO = fakeBankService.fetchTransferHistory(customerId,debitBankAccountNumber);
        assertThat(gson.toJson(actualTransferHistoryResponseDTO)).isEqualTo(gson.toJson(getTransferHistoryResponseDTO()));
    }


}
