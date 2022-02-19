package com.interview.fakebank.factory;

import com.interview.fakebank.dto.common.TransactionDTO;
import com.interview.fakebank.dto.common.TransactionType;
import com.interview.fakebank.dto.createAccount.BankAccountRequestDTO;
import com.interview.fakebank.dto.createAccount.BankAccountResponseDTO;
import com.interview.fakebank.dto.fetchBalance.FetchBalanceResponseDTO;
import com.interview.fakebank.dto.transfer.BankTransferRequestDTO;
import com.interview.fakebank.dto.transfer.BankTransferResponseDTO;
import com.interview.fakebank.dto.transferHistory.TransferHistoryResponseDTO;
import com.interview.fakebank.model.BankAccount;
import com.interview.fakebank.model.BankTransfer;
import com.interview.fakebank.model.TransferStatus;

import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    public static Long customerId = 1L;
    public static Long creditBankAccountNumber = 20220502L;
    public static Long debitBankAccountNumber = 20220502L;
    public static Long openingBalance = 1000L;
    public static Long currentBalance = 990L;
    public static Long transferAmount = 10L;
    public static Long bankTransferId = 765L;

    public static BankAccountRequestDTO getBankAccountDTO() {
        BankAccountRequestDTO bankAccountRequestDTO = new BankAccountRequestDTO(customerId, openingBalance);
        return bankAccountRequestDTO;
    }

    public static BankAccountResponseDTO getBankAccountResponseDTO() {
        BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
        bankAccountResponseDTO.setAccountNumber(creditBankAccountNumber);
        return bankAccountResponseDTO;
    }

    public static FetchBalanceResponseDTO getFetchBalanceResponseDTO() {
        FetchBalanceResponseDTO fetchBalanceResponseDTO = new FetchBalanceResponseDTO();
        fetchBalanceResponseDTO.setCurrentBalance(openingBalance);
        return fetchBalanceResponseDTO;
    }

    public static BankAccount getCreditBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomerId(customerId);
        bankAccount.setOpeningBalance(openingBalance);
        bankAccount.setAccountNumber(creditBankAccountNumber);
        bankAccount.setCurrentBalance(openingBalance);
        return bankAccount;
    }

    public static BankAccount getDebitBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomerId(customerId);
        bankAccount.setOpeningBalance(openingBalance);
        bankAccount.setAccountNumber(debitBankAccountNumber);
        bankAccount.setCurrentBalance(openingBalance);
        return bankAccount;
    }

    public static BankAccount getDebitBankAccountAfterTransfer() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomerId(customerId);
        bankAccount.setOpeningBalance(openingBalance);
        bankAccount.setAccountNumber(debitBankAccountNumber);
        bankAccount.setCurrentBalance(currentBalance);
        return bankAccount;
    }

    public static BankTransfer getBankTransfer() {
        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setId(bankTransferId);
        bankTransfer.setCustomerId(customerId);
        bankTransfer.setDebitAccount(debitBankAccountNumber);
        bankTransfer.setCreditAccount(creditBankAccountNumber);
        bankTransfer.setTransferAmount(transferAmount);
        bankTransfer.setStatus(TransferStatus.PENDING);
        return bankTransfer;
    }

    public static List<BankTransfer> getBankTransferListWithSuccessStatus() {
        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setId(bankTransferId);
        bankTransfer.setCustomerId(customerId);
        bankTransfer.setDebitAccount(debitBankAccountNumber);
        bankTransfer.setCreditAccount(creditBankAccountNumber);
        bankTransfer.setTransferAmount(transferAmount);
        bankTransfer.setStatus(TransferStatus.SUCCESSFUL);
        List<BankTransfer> bankTransferList = new ArrayList<>();
        bankTransferList.add(bankTransfer);
        return bankTransferList;
    }


    public static BankTransferResponseDTO getBankTransferResponseDTO() {
        BankTransferResponseDTO bankTransferResponseDTO = new BankTransferResponseDTO();
        bankTransferResponseDTO.setTransferStatus(TransferStatus.SUCCESSFUL);
        return bankTransferResponseDTO;
    }

    public static BankTransferRequestDTO getBankTransferRequestDTO() {
        BankTransferRequestDTO bankTransferRequestDTO = new BankTransferRequestDTO();
        bankTransferRequestDTO.setCustomerId(customerId);
        bankTransferRequestDTO.setCreditAccount(creditBankAccountNumber);
        bankTransferRequestDTO.setTransferAmount(transferAmount);
        bankTransferRequestDTO.setDebitAccount(debitBankAccountNumber);
        return bankTransferRequestDTO;
    }

    public static TransferHistoryResponseDTO getTransferHistoryResponseDTO() {
        TransferHistoryResponseDTO transferHistoryResponseDTO = new TransferHistoryResponseDTO();
        transferHistoryResponseDTO.setCustomerId(customerId);
        transferHistoryResponseDTO.setOpeningBalance(openingBalance);
        transferHistoryResponseDTO.setCurrentBalance(currentBalance);
        transferHistoryResponseDTO.setAccountNumber(debitBankAccountNumber);
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(new TransactionDTO(
                TransactionType.DEBIT,
                transferAmount,
                creditBankAccountNumber
        ));
        transferHistoryResponseDTO.setTransactionDTOList(transactionDTOList);
        return transferHistoryResponseDTO;
    }

}
