package com.interview.fakebank.dto.createAccount;

import com.interview.fakebank.model.ErrorMessage;

public class BankAccountResponseDTO {
    private ErrorMessage errorMessage;
    private Long accountNumber;

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
