package ru.sbt.test.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.domain.AccountOperation;

import java.util.List;

@Repository
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findAllByAccount(Account account);
}
