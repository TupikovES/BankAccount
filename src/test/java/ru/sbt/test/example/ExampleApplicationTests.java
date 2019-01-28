package ru.sbt.test.example;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.sbt.test.example.controller.AccountController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleApplicationTests {

    @Autowired
    private AccountController accountController;

    @Test
    public void contextLoads() {
        assertThat(accountController).isNotNull();
    }

}

