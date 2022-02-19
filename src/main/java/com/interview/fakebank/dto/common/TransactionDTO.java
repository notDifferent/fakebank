package com.interview.fakebank.dto.common;

public class TransactionDTO {
    private TransactionType transactionType;
    private Long transactionAmount;
    private Long transactionAccountNumber;

    public TransactionDTO(TransactionType transactionType, Long transactionAmount, Long transactionAccountNumber) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.transactionAccountNumber = transactionAccountNumber;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Long getTransactionAccountNumber() {
        return transactionAccountNumber;
    }

    public void setTransactionAccountNumber(Long transactionAccountNumber) {
        this.transactionAccountNumber = transactionAccountNumber;
    }
}
