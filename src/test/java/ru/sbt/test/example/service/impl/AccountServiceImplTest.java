package ru.sbt.test.example.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.domain.AccountOperation;
import ru.sbt.test.example.entity.OperationResult;
import ru.sbt.test.example.repository.AccountOperationRepository;
import ru.sbt.test.example.repository.AccountRepository;
import ru.sbt.test.example.service.AccountOperationService;
import ru.sbt.test.example.service.AccountService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountOperationService operationService;

    @MockBean
    private AccountOperationRepository operationRepository;

    @Test
    public void getAccountByNumber() {
        Account account = new Account(100, 5000L);
        account.setId(10L);
        when(this.accountRepository.findAccountByAccountNumber(100)).thenReturn(account);
        Account testAccount = accountService.getAccountByNumber(100);
        assertThat(testAccount.getId()).isEqualTo(account.getId());
        assertThat(testAccount.getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(testAccount.getCash()).isEqualTo(account.getCash());
    }

    @Test
    public void refill_complete() {
        Account account = new Account(100, 5000L);
        account.setId(10L);
        when(this.accountRepository.findAccountByAccountNumber(100)).thenReturn(account);
        OperationResult operationResult = accountService.refill(100, 600L);
        assertThat(operationResult.getStatusCode()).isEqualTo(OperationResult.CODE.COMPLETE.getCode());
        assertThat(operationResult.getStatusMessage()).isEqualTo(OperationResult.CODE.COMPLETE.getMessage());
        assertThat(operationResult.getAccount().getId()).isEqualTo(account.getId());
        assertThat(operationResult.getAccount().getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(operationResult.getAccount().getCash()).isEqualTo(account.getCash());
    }

    @Test
    public void refill_accountNotFound() {
        when(this.accountRepository.findAccountByAccountNumber(100)).thenReturn(null);
        OperationResult operationResult = accountService.refill(100, 500L);
        assertThat(operationResult.getStatusCode()).isEqualTo(OperationResult.CODE.ACCOUNT_NOT_FOUND.getCode());
        assertThat(operationResult.getStatusMessage()).isEqualTo(OperationResult.CODE.ACCOUNT_NOT_FOUND.getMessage());
        assertThat(operationResult.getAccount()).isNull();
    }

    @Test
    public void withdrawal_complete() {
        Account account = new Account(100, 5000L);
        account.setId(10L);
        when(this.accountRepository.findAccountByAccountNumber(100)).thenReturn(account);
        OperationResult operationResult = accountService.withdrawal(100, 600L);
        assertThat(operationResult.getStatusCode()).isEqualTo(OperationResult.CODE.COMPLETE.getCode());
        assertThat(operationResult.getStatusMessage()).isEqualTo(OperationResult.CODE.COMPLETE.getMessage());
        assertThat(operationResult.getAccount().getId()).isEqualTo(account.getId());
        assertThat(operationResult.getAccount().getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(operationResult.getAccount().getCash()).isEqualTo(account.getCash());
    }

    @Test
    public void withdrawal_accountNotFound() {
        when(this.accountRepository.findAccountByAccountNumber(100)).thenReturn(null);
        OperationResult operationResult = accountService.withdrawal(100, 500L);
        assertThat(operationResult.getStatusCode()).isEqualTo(OperationResult.CODE.ACCOUNT_NOT_FOUND.getCode());
        assertThat(operationResult.getStatusMessage()).isEqualTo(OperationResult.CODE.ACCOUNT_NOT_FOUND.getMessage());
        assertThat(operationResult.getAccount()).isNull();
    }

    @Test
    public void withdrawal_insufficientFunds() {
        Account account = new Account(100, 0L);
        account.setId(10L);
        when(this.accountRepository.findAccountByAccountNumber(100)).thenReturn(account);
        OperationResult operationResult = accountService.withdrawal(100, 600L);
        assertThat(operationResult.getStatusCode()).isEqualTo(OperationResult.CODE.INSUFFICIENT_FUNDS.getCode());
        assertThat(operationResult.getStatusMessage()).isEqualTo(OperationResult.CODE.INSUFFICIENT_FUNDS.getMessage());
        assertThat(operationResult.getAccount().getId()).isEqualTo(account.getId());
        assertThat(operationResult.getAccount().getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(operationResult.getAccount().getCash()).isEqualTo(account.getCash());
    }
}