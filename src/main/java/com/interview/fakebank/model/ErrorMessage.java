package com.interview.fakebank.model;

public enum ErrorMessage {

    GENERIC_MESSAGE("something went wrong"),
    INVALID_CUSTOMER("Invalid Customer Id"),
    CREDIT_ACCOUNT_DOES_NOT_EXIST("credit account does not exist"),
    DEBIT_ACCOUNT_DOES_NOT_EXIST("debit account does not exist"),
    INSUFFICIENT_BALANCE("In-Sufficient Balance"),
    ACCOUNT_DOES_NOT_EXIST("Bank Account Does not Exists")
    ;

    ErrorMessage(String s) {
    }
}
