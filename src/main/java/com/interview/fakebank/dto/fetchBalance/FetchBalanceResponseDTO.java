package com.interview.fakebank.dto.fetchBalance;

import com.interview.fakebank.model.ErrorMessage;

public class FetchBalanceResponseDTO {

    private ErrorMessage errorMessage;
    private Long currentBalance;

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Long currentBalance) {
        this.currentBalance = currentBalance;
    }
}
