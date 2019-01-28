package ru.sbt.test.example.service;

import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.entity.OperationResult;

public interface AccountService {

    Account getAccountByNumber(Integer accountNumber);
    OperationResult refill(Integer accountNumbert, Long amount);
    OperationResult withdrawal(Integer accountNumber, Long amount);

}