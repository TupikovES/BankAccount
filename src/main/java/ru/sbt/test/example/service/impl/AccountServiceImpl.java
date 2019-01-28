package ru.sbt.test.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.domain.AccountOperation;
import ru.sbt.test.example.domain.OperationType;
import ru.sbt.test.example.entity.OperationResult;
import ru.sbt.test.example.entity.OperationResult.CODE;
import ru.sbt.test.example.repository.AccountRepository;
import ru.sbt.test.example.service.AccountOperationService;
import ru.sbt.test.example.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountOperationService accountOperationService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountOperationService accountOperationService) {
        this.accountRepository = accountRepository;
        this.accountOperationService = accountOperationService;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccountByNumber(Integer accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public OperationResult refill(Integer accountNumber, Long amount) {

        Account account = accountRepository.findAccountByAccountNumber(accountNumber);

        if (account == null) {
            return new OperationResult(CODE.ACCOUNT_NOT_FOUND, null);
        }

        account.addCash(amount);
        Account save = accountRepository.save(account);
        accountOperationService.add(new AccountOperation(save, OperationType.REFILL, amount, CODE.COMPLETE.getCode()));

        return new OperationResult(CODE.COMPLETE, account);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public OperationResult withdrawal(Integer accountNumber, Long amount) {

        Account account = accountRepository.findAccountByAccountNumber(accountNumber);

        if (account == null) {
            return new OperationResult(CODE.ACCOUNT_NOT_FOUND, null);
        }

        Long cash = account.getCash();
        if (cash >= amount) {

            account.setCash(cash - amount);
            Account save = accountRepository.save(account);
            accountOperationService.add(
                    new AccountOperation(save, OperationType.WITHDRAWAL, amount, CODE.COMPLETE.getCode())
            );

            return new OperationResult(CODE.COMPLETE, account);

        }

        accountOperationService.add(
                new AccountOperation(account, OperationType.WITHDRAWAL, amount, CODE.INSUFFICIENT_FUNDS.getCode())
        );

        return new OperationResult(CODE.INSUFFICIENT_FUNDS, account);
    }
}