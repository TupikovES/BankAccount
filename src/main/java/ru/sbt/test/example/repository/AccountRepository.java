package ru.sbt.test.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import ru.sbt.test.example.domain.Account;

import javax.persistence.LockModeType;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Get account with locking
     *
     * @param accountNumber Account number
     * @return Account entity or {@code null}
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    Account findAccountByAccountNumber(Integer accountNumber);
}