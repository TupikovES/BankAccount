package ru.sbt.test.example.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbt.test.example.domain.Account;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @Rollback
    public void save() {
        Account account = new Account(20, 2000L);
        Account save = this.accountRepository.save(account);
        assertThat(save.getId()).isNotNull();
        assertThat(save.getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(save.getCash()).isEqualTo(account.getCash());
    }

    @Test
    @Rollback
    public void findByAccountNumber() {
        Account account = new Account(20, 2000L);
        this.testEntityManager.persist(account);
        Account save = this.accountRepository.findAccountByAccountNumber(20);
        assertThat(save.getId()).isNotNull();
        assertThat(save.getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(save.getCash()).isEqualTo(account.getCash());
    }
}