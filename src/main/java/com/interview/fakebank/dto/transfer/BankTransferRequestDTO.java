package com.interview.fakebank.dto.transfer;

import com.interview.fakebank.model.BankTransfer;
import com.interview.fakebank.model.TransferStatus;

import javax.validation.constraints.NotNull;

public class BankTransferRequestDTO {

    @NotNull(message = "cannot be null")
    private Long customerId;

    @NotNull(message = "cannot be null")
    private Long debitAccount;

    @NotNull(message = "cannot be null")
    private Long creditAccount;

    @NotNull(message = "cannot be null")
    private Long transferAmount;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Long debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Long getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Long creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }


    public BankTransfer toBankTransfer() {
        BankTransfer bankTransfer = new BankTransfer();
        bankTransfer.setCustomerId(customerId);
        bankTransfer.setDebitAccount(debitAccount);
        bankTransfer.setCreditAccount(creditAccount);
        bankTransfer.setTransferAmount(transferAmount);
        bankTransfer.setStatus(TransferStatus.PENDING);
        return bankTransfer;
    }
}
