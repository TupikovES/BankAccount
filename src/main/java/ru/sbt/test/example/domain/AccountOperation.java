package ru.sbt.test.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;
import ru.sbt.test.example.controller.Views;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class AccountOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "acct_id")
    @JsonView(Views.AccountOperationView.class)
    private Account account;

    @Enumerated
    private OperationType operationType;
    private Long operationAmount;
    private Integer operationResultCode;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Calendar timestamp;

    public AccountOperation() {
    }

    public AccountOperation(Account account, OperationType operationType, Long operationAmount, Integer operationResultCode) {
        this.account = account;
        this.operationType = operationType;
        this.operationAmount = operationAmount;
        this.operationResultCode = operationResultCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public Long getOperationAmount() {
        return operationAmount;
    }

    public void setOperationAmount(Long operationAmount) {
        this.operationAmount = operationAmount;
    }

    public Integer getOperationResultCode() {
        return operationResultCode;
    }

    public void setOperationResultCode(Integer operationResultCode) {
        this.operationResultCode = operationResultCode;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountOperation that = (AccountOperation) o;
        return Objects.equals(account, that.account) &&
                operationType == that.operationType &&
                Objects.equals(operationAmount, that.operationAmount) &&
                Objects.equals(operationResultCode, that.operationResultCode) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, operationType, operationAmount, operationResultCode, timestamp);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccountOperation{");
        sb.append("id=").append(id);
        sb.append(", account=").append(account);
        sb.append(", operationType=").append(operationType);
        sb.append(", operationAmount=").append(operationAmount);
        sb.append(", operationResultCode=").append(operationResultCode);
        sb.append(", timestamp=").append(timestamp);
        sb.append('}');
        return sb.toString();
    }
}
