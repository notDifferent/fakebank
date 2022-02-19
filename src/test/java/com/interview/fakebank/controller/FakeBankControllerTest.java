package com.interview.fakebank.controller;

import static com.interview.fakebank.factory.ObjectFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.interview.fakebank.dto.createAccount.BankAccountRequestDTO;
import com.interview.fakebank.dto.transfer.BankTransferRequestDTO;
import com.interview.fakebank.dto.transfer.BankTransferResponseDTO;
import com.interview.fakebank.dto.transferHistory.TransferHistoryResponseDTO;
import com.interview.fakebank.service.FakeBankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FakeBankControllerTest {

    @InjectMocks
    private FakeBankController fakeBankController;

    @Mock
    private FakeBankService fakeBankService;

    Gson gson = new Gson();

    @Test
    public void testCreateAccount() {
        when(fakeBankService.createBankAccount(any(BankAccountRequestDTO.class))).thenReturn(getBankAccountResponseDTO());
        Long actualBankAccountNumber = fakeBankController.createAccount(getBankAccountDTO()).getAccountNumber();
        assertThat(actualBankAccountNumber).isEqualTo(creditBankAccountNumber);
    }

    @Test
    public void testTransfer() {
        when(fakeBankService.transferAmount(any(BankTransferRequestDTO.class))).thenReturn(getBankTransferResponseDTO());
        BankTransferResponseDTO actualBankTransferResponseDTO = fakeBankController.transfer(getBankTransferRequestDTO());
        assertThat(actualBankTransferResponseDTO.getMessage()).isEqualTo(getBankTransferResponseDTO().getMessage());
        assertThat(actualBankTransferResponseDTO.getTransferStatus()).isEqualTo(getBankTransferResponseDTO().getTransferStatus());
    }


    @Test
    public void testFetchBalance() {
        when(fakeBankService.fetchBalance(any(Long.class), any(Long.class))).thenReturn(getFetchBalanceResponseDTO());
        Long actualBalance = fakeBankController.fetchBalance(creditBankAccountNumber, customerId).getCurrentBalance();
        assertThat(actualBalance).isEqualTo(openingBalance);
    }

    @Test
    public void testFetchTransferHistory() {
        when(fakeBankService.fetchTransferHistory(any(Long.class), any(Long.class))).thenReturn(getTransferHistoryResponseDTO());
        TransferHistoryResponseDTO actualTransferHistoryResponseDTO = fakeBankController.fetchTransferHistory(debitBankAccountNumber, customerId);
        assertThat(gson.toJson(actualTransferHistoryResponseDTO)).isEqualTo(gson.toJson(getTransferHistoryResponseDTO()));
    }

}
