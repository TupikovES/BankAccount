package ru.sbt.test.example.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.domain.AccountOperation;
import ru.sbt.test.example.domain.OperationType;
import ru.sbt.test.example.entity.OperationResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountOperationRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AccountOperationRepository accountOperationRepository;

    @Test
    @Rollback
    public void save() {
        Account account = testEntityManager.persist(new Account(25, 400L));
        AccountOperation operation = new AccountOperation(
                account,
                OperationType.REFILL,
                200L,
                OperationResult.CODE.COMPLETE.getCode()
        );
        AccountOperation save = accountOperationRepository.save(operation);
        assertThat(save.getId()).isNotNull();
        assertThat(save.getOperationAmount()).isEqualTo(operation.getOperationAmount());
        assertThat(save.getOperationResultCode()).isEqualTo(operation.getOperationResultCode());
        assertThat(save.getOperationType()).isEqualTo(operation.getOperationType());
        assertThat(save.getAccount().getId()).isEqualTo(account.getId());
        assertThat(save.getAccount().getAccountNumber()).isEqualTo(account.getAccountNumber());
        assertThat(save.getAccount().getCash()).isEqualTo(account.getCash());
    }


    @Test
    @Rollback
    public void findAllByAccount() {
        Account account = testEntityManager.persist(new Account(35, 800L));
        AccountOperation operation1 = new AccountOperation(
                account,
                OperationType.REFILL,
                200L,
                OperationResult.CODE.COMPLETE.getCode()
        );

        AccountOperation operation2 = new AccountOperation(
                account,
                OperationType.WITHDRAWAL,
                300L,
                OperationResult.CODE.COMPLETE.getCode()
        );

        testEntityManager.persist(operation1);
        testEntityManager.persist(operation2);
        List<AccountOperation> allByAccount = accountOperationRepository.findAllByAccount(account);

        assertThat(allByAccount.size()).isEqualTo(2);

        assertThat(allByAccount.get(0)).isEqualTo(operation1);
        assertThat(allByAccount.get(1)).isEqualTo(operation2);
    }

}