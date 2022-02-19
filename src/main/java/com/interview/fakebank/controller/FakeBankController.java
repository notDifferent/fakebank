package com.interview.fakebank.controller;

import com.google.gson.Gson;
import com.interview.fakebank.dto.createAccount.BankAccountRequestDTO;
import com.interview.fakebank.dto.createAccount.BankAccountResponseDTO;
import com.interview.fakebank.dto.fetchBalance.FetchBalanceResponseDTO;
import com.interview.fakebank.dto.transfer.BankTransferRequestDTO;
import com.interview.fakebank.dto.transfer.BankTransferResponseDTO;
import com.interview.fakebank.dto.transferHistory.TransferHistoryResponseDTO;
import com.interview.fakebank.service.FakeBankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/fakebank/")
public class FakeBankController {

    @Autowired
    FakeBankService fakeBankService;

    private final Logger logger = LogManager.getLogger();

    private final Gson gson = new Gson();

    /**
     * This api is for creating bank account, Account creation will happen only for the registered customer ids given in customer.json
     * @param bankAccountRequestDTO {"customerId": 1,"openingBalance": "100000"}
     * @return {"errorMessage": null,"accountNumber": 1644146306822}
     */
    @PostMapping("/createAccount")
    BankAccountResponseDTO createAccount(@RequestBody @Valid BankAccountRequestDTO bankAccountRequestDTO) {
        logger.info("create account : {}", gson.toJson(bankAccountRequestDTO));
        return fakeBankService.createBankAccount(bankAccountRequestDTO);
    }

    /**
     * This api is for making a transfer between two accounts, Account should have the sufficient balance to make transfer
     * @param bankTransferRequestDTO {"customerId": 3,"debitAccount": 1644146306822,"creditAccount": 1644145430072,"transferAmount": 10000}
     * @return {"transferStatus": "SUCCESSFUL","message": null}
     */
    @PostMapping("/transfer")
    BankTransferResponseDTO transfer(@RequestBody @Valid BankTransferRequestDTO bankTransferRequestDTO) {
        logger.info("transfer : {}", gson.toJson(bankTransferRequestDTO));
        return fakeBankService.transferAmount(bankTransferRequestDTO);
    }

    /**
     * This api is for fetching the balance of an account, customer can fetch balance of his own account only.
     * @param accountNumber 1644146306822
     * @param customerId 2
     * @return {"errorMessage": null,"currentBalance": 200000}
     */
    @GetMapping("/fetchBalance")
    FetchBalanceResponseDTO fetchBalance(
            @RequestParam(value = "account_number") @NotNull Long accountNumber,
            @RequestParam(value = "customer_id") @NotNull Long customerId
    ) {
        logger.info("fetch balance customer id : {} , account number : {}", customerId, accountNumber);
        return fakeBankService.fetchBalance(customerId, accountNumber);
    }

    /**
     * This api is for fetch the transfer history of an account, customer can fetch history of his own accounts only.
     * @param accountNumber 1644146306822
     * @param customerId 2
     * @return {"errorMessage":null,"customerId":2,"accountNumber":1644148676260,"openingBalance":200000,"currentBalance":100000,"transactionDTOList":[{"transactionType":"DEBIT","transactionAmount":100000,"transactionAccountNumber":1644148664661}]}
     */
    @GetMapping("/fetchTransferHistory")
    TransferHistoryResponseDTO fetchTransferHistory(
            @RequestParam("account_number") @Valid @NotNull Long accountNumber,
            @RequestParam("customer_id") @Valid @NotNull Long customerId
    ) {
        logger.info("fetch balance customer id : {} , account number : {}", customerId, accountNumber);
        return fakeBankService.fetchTransferHistory(customerId, accountNumber);
    }




}
