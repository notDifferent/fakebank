package com.interview.fakebank.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.interview.fakebank.dto.common.TransactionDTO;
import com.interview.fakebank.dto.common.TransactionType;
import com.interview.fakebank.dto.createAccount.BankAccountRequestDTO;
import com.interview.fakebank.dto.createAccount.BankAccountResponseDTO;
import com.interview.fakebank.dto.fetchBalance.FetchBalanceResponseDTO;
import com.interview.fakebank.dto.transfer.BankTransferRequestDTO;
import com.interview.fakebank.dto.transfer.BankTransferResponseDTO;
import com.interview.fakebank.dto.transferHistory.TransferHistoryResponseDTO;
import com.interview.fakebank.model.*;
import com.interview.fakebank.repository.AccountRepository;
import com.interview.fakebank.repository.TransferRepository;
import com.interview.fakebank.service.FakeBankService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FakeBankServiceImpl implements FakeBankService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransferRepository transferRepository;


    @Value("${fakebank.customer.file.address}")
    String customerFileAddress = "classpath:customer.json";

    private List<BankCustomer> bankCustomerList = new ArrayList<>() {{
        add(new BankCustomer(1L, "Temp"));
    }};

    private final Logger logger = LogManager.getLogger();
    private final Gson gson = new Gson();

    public void populateCustomers() {
        try {
            Reader reader = new FileReader(ResourceUtils.getFile(customerFileAddress));
            Type listType = new TypeToken<ArrayList<BankCustomer>>(){}.getType();
            bankCustomerList = gson.fromJson(reader, listType);
            bankCustomerList.forEach(it -> {
                logger.info("customer id : {} , name : {}", it.getId(), it.getName());
            });
        } catch (Exception exception) {
            logger.error("exception", exception);
        }
    }

    @PostConstruct
    public void init() {
        populateCustomers();
    }

    public Boolean checkValidCustomerId(Long id) {
        for (BankCustomer bc : bankCustomerList) {
            if (Objects.equals(bc.getId(), id)) return true;
        }
        return false;
    }

    @Override
    public BankAccountResponseDTO createBankAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        BankAccountResponseDTO bankAccountResponseDTO = new BankAccountResponseDTO();
        try {
            if (checkValidCustomerId(bankAccountRequestDTO.getCustomerId())) {
                BankAccount bankAccount = bankAccountRequestDTO.toBankAccount();
                BankAccount savedBankAccount = accountRepository.save(bankAccount);
                bankAccountResponseDTO.setAccountNumber(savedBankAccount.getAccountNumber());
                logger.info("Customer Id : {}, Account Number : {}, successfully created account", savedBankAccount.getCustomerId(), savedBankAccount.getAccountNumber());
            } else {
                bankAccountResponseDTO.setErrorMessage(ErrorMessage.INVALID_CUSTOMER);
                logger.info("Customer Id : {}, Invalid customer while error creating account", bankAccountRequestDTO.getCustomerId());
            }
        } catch (Exception exception) {
            logger.error("Customer Id : {}, error creating account", bankAccountRequestDTO.getCustomerId(), exception);
            bankAccountResponseDTO.setErrorMessage(ErrorMessage.GENERIC_MESSAGE);
        }
        return bankAccountResponseDTO;
    }

    @Override
    public BankTransferResponseDTO transferAmount(BankTransferRequestDTO bankTransferRequestDTO) {
        /**
         * save the transfer request
         * check if debit account exist
         * check if the account holder have this amount in the debit account
         * check if credit account exist
         * make the transfer
         */
        BankTransferResponseDTO bankTransferResponseDTO = new BankTransferResponseDTO();
        try {
            BankTransfer bankTransfer = bankTransferRequestDTO.toBankTransfer();
            BankTransfer savedTransferRequest = transferRepository.save(bankTransfer);
            logger.info("Customer Id : {}, Account Number : {}, saved the transfer request", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount());
            BankAccount debitAccount = accountRepository.findByCustomerIdAndAccountNumber(
                    savedTransferRequest.getCustomerId(),
                    savedTransferRequest.getDebitAccount()
            );
            BankAccount creditAccount = accountRepository.findByAccountNumber(savedTransferRequest.getCreditAccount());

            if (creditAccount != null && debitAccount != null && debitAccount.getCurrentBalance() > savedTransferRequest.getTransferAmount()) {
                Long debitAccountUpdatedBalance = debitAccount.getCurrentBalance() - savedTransferRequest.getTransferAmount();
                Long creditAccountUpdatedBalance = creditAccount.getCurrentBalance() + savedTransferRequest.getTransferAmount();
                accountRepository.updateCurrentBalance(debitAccount.getAccountNumber(), debitAccountUpdatedBalance);
                accountRepository.updateCurrentBalance(creditAccount.getAccountNumber(), creditAccountUpdatedBalance);
                bankTransferResponseDTO.setTransferStatus(TransferStatus.SUCCESSFUL);
                logger.info("Customer Id : {}, Account Number : {} , transfer has been successful", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount());
            } else if (debitAccount == null){
                bankTransferResponseDTO.setTransferStatus(TransferStatus.FAILED);
                bankTransferResponseDTO.setMessage(ErrorMessage.DEBIT_ACCOUNT_DOES_NOT_EXIST);
                logger.info("Customer Id : {}, Account Number : {} , Error executing transfer request : {}", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount(), ErrorMessage.DEBIT_ACCOUNT_DOES_NOT_EXIST);
            } else if (debitAccount.getCurrentBalance() <= savedTransferRequest.getTransferAmount()) {
                bankTransferResponseDTO.setTransferStatus(TransferStatus.FAILED);
                bankTransferResponseDTO.setMessage(ErrorMessage.INSUFFICIENT_BALANCE);
                logger.info("Customer Id : {}, Account Number : {} , Error executing transfer request : {}", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount(), ErrorMessage.INSUFFICIENT_BALANCE);
            } else if (creditAccount == null) {
                bankTransferResponseDTO.setTransferStatus(TransferStatus.FAILED);
                bankTransferResponseDTO.setMessage(ErrorMessage.CREDIT_ACCOUNT_DOES_NOT_EXIST);
                logger.info("Customer Id : {}, Account Number : {} , Error executing transfer request : {}", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount(), ErrorMessage.CREDIT_ACCOUNT_DOES_NOT_EXIST);
            }
            transferRepository.updateTransferStatusAndMessageById(
                    savedTransferRequest.getId(),
                    bankTransferResponseDTO.getTransferStatus(),
                    bankTransferResponseDTO.getMessage()
            );
            logger.info("Customer Id : {}, Account Number : {} , updated status of transfer request id : {} to {}", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount(), savedTransferRequest.getId(), bankTransferResponseDTO.getTransferStatus());
        } catch (Exception exception) {
            logger.error("Customer Id : {}, Account Number : {} , Error Executing transfer request", bankTransferRequestDTO.getCustomerId(), bankTransferRequestDTO.getDebitAccount(), exception);
            bankTransferResponseDTO.setMessage(ErrorMessage.GENERIC_MESSAGE);
        }
        return bankTransferResponseDTO;
    }

    @Override
    public FetchBalanceResponseDTO fetchBalance(Long customerId, Long accountNumber) {
        FetchBalanceResponseDTO fetchBalanceResponseDTO = new FetchBalanceResponseDTO();
        try {
            BankAccount bankAccount = accountRepository.findByCustomerIdAndAccountNumber(customerId, accountNumber);
            if (bankAccount != null) {
                fetchBalanceResponseDTO.setCurrentBalance(bankAccount.getCurrentBalance());
                logger.info("Customer Id : {}, Account Number : {} , fetched balance", customerId, accountNumber);
            } else {
                fetchBalanceResponseDTO.setErrorMessage(ErrorMessage.ACCOUNT_DOES_NOT_EXIST);
                logger.info("Customer Id : {}, Account Number : {} , Error fetch balance Account does not exist", customerId, accountNumber);
            }
        } catch (Exception exception) {
            logger.error("Customer Id : {}, Account Number : {} , Error Fetching balance", customerId, accountNumber, exception);
            fetchBalanceResponseDTO.setErrorMessage(ErrorMessage.GENERIC_MESSAGE);
        }
        return fetchBalanceResponseDTO;
    }


    @Override
    public TransferHistoryResponseDTO fetchTransferHistory(Long customerId, Long accountNumber) {
        TransferHistoryResponseDTO transferHistoryResponseDTO = new TransferHistoryResponseDTO();
        try {
            BankAccount bankAccount = accountRepository.findByCustomerIdAndAccountNumber(customerId, accountNumber);
            if (bankAccount == null) {
                transferHistoryResponseDTO.setErrorMessage(ErrorMessage.ACCOUNT_DOES_NOT_EXIST);
                logger.info("Customer Id : {}, Account Number : {} , Error fetching transfer history Account does not exist", customerId, accountNumber);
                return transferHistoryResponseDTO;
            }
            List<BankTransfer> bankTransferList = transferRepository.findByDebitAccountOrCreditAccount(accountNumber, accountNumber);
            transferHistoryResponseDTO.setCustomerId(customerId);
            transferHistoryResponseDTO.setAccountNumber(accountNumber);
            transferHistoryResponseDTO.setOpeningBalance(bankAccount.getOpeningBalance());
            transferHistoryResponseDTO.setCurrentBalance(bankAccount.getCurrentBalance());
            logger.info("Customer Id : {}, Account Number : {} , fetched transfer history", customerId, accountNumber);
            List<TransactionDTO> transactionDTOList = new ArrayList<>();
            bankTransferList.forEach(it -> {
                if (it.getStatus().equals(TransferStatus.SUCCESSFUL)) {
                    TransactionDTO transactionDTO = new TransactionDTO(
                            TransactionType.CREDIT,
                            it.getTransferAmount(),
                            it.getDebitAccount()
                    );
                    if (it.getDebitAccount().equals(accountNumber)) {
                        transactionDTO.setTransactionType(TransactionType.DEBIT);
                        transactionDTO.setTransactionAccountNumber(it.getCreditAccount());
                    }
                    transactionDTOList.add(transactionDTO);
                }
            });
            transferHistoryResponseDTO.setTransactionDTOList(transactionDTOList);
        } catch (Exception exception) {
            logger.info("Customer Id : {}, Account Number : {} , Error fetching transfer history", customerId, accountNumber, exception);
            transferHistoryResponseDTO.setErrorMessage(ErrorMessage.GENERIC_MESSAGE);
        }
        return transferHistoryResponseDTO;
    }

}
