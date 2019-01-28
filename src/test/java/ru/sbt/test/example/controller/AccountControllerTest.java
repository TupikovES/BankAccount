package ru.sbt.test.example.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import ru.sbt.test.example.domain.Account;
import ru.sbt.test.example.entity.OperationResult;
import ru.sbt.test.example.repository.AccountOperationRepository;
import ru.sbt.test.example.repository.AccountRepository;
import ru.sbt.test.example.service.AccountOperationService;
import ru.sbt.test.example.service.AccountService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountOperationService accountOperationService;

    @MockBean
    private AccountOperationRepository accountOperationRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void getAccount() throws Exception {
        Account account = new Account(1, 3000L);
        account.setId(1L);
        when(this.accountService.getAccountByNumber(1)).thenReturn(account);
        this.mockMvc
                .perform(get("/accounts/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.accountNumber", is(1)))
                .andExpect(jsonPath("$.cash", is(3000)));

    }

    @Test
    public void refill() throws Exception {
        Account account = new Account(1, 3500L);
        account.setId(1L);
        when(this.accountService.refill(1, 500L)).thenReturn(new OperationResult(OperationResult.CODE.COMPLETE, account));
        this.mockMvc
                .perform(post("/accounts/1/refill/500"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(0)))
                .andExpect(jsonPath("$.statusMessage", is("Operation complete")))
                .andExpect(jsonPath("$.account.id", is(1)))
                .andExpect(jsonPath("$.account.accountNumber", is(1)))
                .andExpect(jsonPath("$.account.cash", is(3500)));
    }

    @Test
    public void withdrawal() throws Exception {
        Account account = new Account(1, 3500L);
        account.setId(1L);
        when(this.accountService.withdrawal(1, 500L)).thenReturn(new OperationResult(OperationResult.CODE.COMPLETE, account));
        this.mockMvc
                .perform(post("/accounts/1/withdrawal/500"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(0)))
                .andExpect(jsonPath("$.statusMessage", is("Operation complete")))
                .andExpect(jsonPath("$.account.id", is(1)))
                .andExpect(jsonPath("$.account.accountNumber", is(1)))
                .andExpect(jsonPath("$.account.cash", is(3500)));
    }
}