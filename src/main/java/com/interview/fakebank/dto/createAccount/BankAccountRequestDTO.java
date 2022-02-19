package com.interview.fakebank.dto.createAccount;

import com.interview.fakebank.model.BankAccount;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

public class BankAccountRequestDTO {

    @NotNull(message = "cannot be null")
    private Long customerId;

    @NotNull(message = "cannot be null")
    private Long openingBalance;

    public BankAccountRequestDTO(@NotNull Long customerId, @NotNull Long openingBalance) {
        this.customerId = customerId;
        this.openingBalance = openingBalance;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Long openingBalance) {
        this.openingBalance = openingBalance;
    }

    public BankAccount toBankAccount() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomerId(customerId);
        bankAccount.setOpeningBalance(openingBalance);
        bankAccount.setCurrentBalance(openingBalance);
        bankAccount.setAccountNumber(getUniqueAccountNumber());
        return bankAccount;
    }

    private Long getUniqueAccountNumber() {
        return (ZonedDateTime.now().toInstant().toEpochMilli());
    }

}
