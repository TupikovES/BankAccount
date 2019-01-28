package ru.sbt.test.example.service;

import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.domain.AccountOperation;

import java.util.List;

public interface AccountOperationService {

    AccountOperation add(AccountOperation operation);
    List<AccountOperation> getOperationHistory(Account account);

}
