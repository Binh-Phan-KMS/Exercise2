package com.binh.exercise2;

import com.binh.exercise2.controller.TaskController;
import com.binh.exercise2.controller.TeamController;
import com.binh.exercise2.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {

    @Autowired
    private UserController userController;

    @Autowired
    private TeamController teamController;

    @Autowired
    private TaskController taskController;

    @Test
    void contextLoads() {
        assertThat(userController).isNotNull();
        assertThat(teamController).isNotNull();
        assertThat(taskController).isNotNull();
    }

}
