package ru.sbt.test.example.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.entity.OperationResult;
import ru.sbt.test.example.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Test
    public void testRefillWrite() throws InterruptedException, ExecutionException {

        accountRepository.save(new Account(1, 3000L));

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<OperationResult>> list = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Callable<OperationResult> callable;
            if (i % 2 == 0) {
                callable = () -> accountService.refill(1, 50L);
            } else {
                callable = () -> accountService.withdrawal(1, 30L);
            }
            list.add(callable);
        }

        executorService.invokeAll(list);
        executorService.shutdown();

        Account account = accountService.getAccountByNumber(1);
        assertThat(account.getCash() == 3200).isTrue();
        accountRepository.delete(account);
    }

}
