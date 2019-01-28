package ru.sbt.test.example.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.entity.OperationResult;
import ru.sbt.test.example.service.AccountService;

/**
 * Account controller
 *
 * @author Evgeniy Tupikov
 *
 * @see Account
 * @see AccountService
 * @see OperationResult
 * @see ResponseEntity
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Find account by number
     *
     * @param accountNumber Account number
     * @return json entity
     *
     */
    @GetMapping("/{accountNumber}")
    @JsonView(Views.AccountView.class)
    public ResponseEntity getAccount(@PathVariable Integer accountNumber) {

        Account account = accountService.getAccountByNumber(accountNumber);
        if (account == null)
            return ResponseEntity.badRequest().body("Account not found");

        return ResponseEntity.ok().body(account);

    }

    /**
     * Refill account
     *
     * @param accountNumber Account number
     * @param amount
     * @return json entity {@link OperationResult}
     */
    @PostMapping("{accountNumber}/refill/{amount}")
    @JsonView(Views.AccountView.class)
    public ResponseEntity refill(@PathVariable Integer accountNumber, @PathVariable Long amount) {

        OperationResult refill = accountService.refill(accountNumber, amount);

        return ResponseEntity.ok().body(refill);
    }

    /**
     * Withdrawal account
     *
     * @param accountNumber Account number
     * @param amount
     * @return json entity {@link OperationResult}
     */
    @PostMapping("/{accountNumber}/withdrawal/{amount}")
    @JsonView(Views.AccountView.class)
    public ResponseEntity withdrawal(@PathVariable Integer accountNumber, @PathVariable Long amount) {

        OperationResult withdrawal = accountService.withdrawal(accountNumber, amount);

        return ResponseEntity.ok().body(withdrawal);
    }

}
