package com.interview.fakebank.dto.transferHistory;

import com.interview.fakebank.dto.common.TransactionDTO;
import com.interview.fakebank.model.ErrorMessage;

import java.util.List;

public class TransferHistoryResponseDTO {

    private ErrorMessage errorMessage;
    private Long customerId;
    private Long accountNumber;
    private Long openingBalance;
    private Long currentBalance;
    private List<TransactionDTO> transactionDTOList;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Long openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }

    public List<TransactionDTO> getTransactionDTOList() {
        return transactionDTOList;
    }

    public void setTransactionDTOList(List<TransactionDTO> transactionDTOList) {
        this.transactionDTOList = transactionDTOList;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}

