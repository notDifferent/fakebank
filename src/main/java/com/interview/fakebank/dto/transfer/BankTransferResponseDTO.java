package com.interview.fakebank.dto.transfer;

import com.interview.fakebank.model.ErrorMessage;
import com.interview.fakebank.model.TransferStatus;

public class BankTransferResponseDTO {

    private TransferStatus transferStatus;
    private ErrorMessage message;

    public BankTransferResponseDTO() {
    }

    public BankTransferResponseDTO(TransferStatus transferStatus, ErrorMessage message) {
        this.transferStatus = transferStatus;
        this.message = message;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }

    public ErrorMessage getMessage() {
        return message;
    }

    public void setMessage(ErrorMessage message) {
        this.message = message;
    }
}