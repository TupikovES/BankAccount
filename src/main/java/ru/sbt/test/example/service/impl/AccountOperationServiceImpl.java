package ru.sbt.test.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.domain.AccountOperation;
import ru.sbt.test.example.repository.AccountOperationRepository;
import ru.sbt.test.example.service.AccountOperationService;

import java.util.List;

@Service
@Transactional
public class AccountOperationServiceImpl implements AccountOperationService {

    private final AccountOperationRepository accountOperationRepository;

    @Autowired
    public AccountOperationServiceImpl(AccountOperationRepository accountOperationRepository) {
        this.accountOperationRepository = accountOperationRepository;
    }

    @Override
    public AccountOperation add(AccountOperation operation) {
        return accountOperationRepository.save(operation);
    }

    @Override
    public List<AccountOperation> getOperationHistory(Account account) {
        return accountOperationRepository.findAllByAccount(account);
    }
}
