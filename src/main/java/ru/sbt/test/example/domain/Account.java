package ru.sbt.test.example.domain;

import com.fasterxml.jackson.annotation.JsonView;
import ru.sbt.test.example.controller.Views;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer accountNumber;
    private Long cash;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonView(Views.AccountView.class)
    private List<AccountOperation> accountOperations;

    public Account() {
    }

    public Account(Integer accountNumber, Long cash) {
        this.accountNumber = accountNumber;
        this.cash = cash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public void addCash(Long cash) {
        this.cash += cash;
    }

    public List<AccountOperation> getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(List<AccountOperation> accountOperations) {
        this.accountOperations = accountOperations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber) &&
                Objects.equals(cash, account.cash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, cash);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Account{");
        sb.append("id=").append(id);
        sb.append(", accountNumber=").append(accountNumber);
        sb.append(", cash=").append(cash);
        sb.append('}');
        return sb.toString();
    }
}
