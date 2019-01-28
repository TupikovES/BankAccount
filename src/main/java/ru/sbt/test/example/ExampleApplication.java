package ru.sbt.test.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.repository.AccountRepository;

@SpringBootApplication
@EnableTransactionManagement
public class ExampleApplication implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Autowired
    public ExampleApplication(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Account account = new Account(5, 3000L);
        accountRepository.save(account);

    }
}