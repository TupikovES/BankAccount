package ru.sbt.test.example.entity;

import ru.sbt.test.example.domain.Account;

/**
 * Operation result entity
 */
public class OperationResult {

    public enum CODE {

        COMPLETE(0, "Operation complete"),
        ACCOUNT_NOT_FOUND(1, "Account not found"),
        INSUFFICIENT_FUNDS(2, "Insufficient funds to complete the operation");

        private int code;
        private String message;

        CODE(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    private int statusCode;
    private String statusMessage;
    private Account account;

    public OperationResult(int statusCode, String statusMessage, Account account) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.account = account;
    }

    public OperationResult(CODE status, Account account) {
        this.statusCode = status.getCode();
        this.statusMessage = status.getMessage();
        this.account = account;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OperationResult{");
        sb.append("statusCode=").append(statusCode);
        sb.append(", statusMessage='").append(statusMessage).append('\'');
        sb.append(", account=").append(account);
        sb.append('}');
        return sb.toString();
    }
}
