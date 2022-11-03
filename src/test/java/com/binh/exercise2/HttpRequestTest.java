package com.binh.exercise2;

import com.binh.exercise2.constant.Role;
import com.binh.exercise2.model.Team;
import com.binh.exercise2.model.User;
import com.binh.exercise2.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HttpRequestTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    @Test
    public void getUsers() {
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/user/list",String.class)).contains("office");
    }

    @Test
    public void getUser() {
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/user/635b589952da8e11048f3cb0",String.class)).contains("error");
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setName("Thai Phan");
        user.setRole(Role.MEMBER);
        assertThat(this.restTemplate.postForObject("http://localhost:" +port+"/user",user,String.class)).contains("role");
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setName("Thai Phan");
        user.setRole(Role.MEMBER);
        this.restTemplate.delete("http://localhost:" +port+"/user/635b7292aab47c04db38d73f");
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/user/635b7292aab47c04db38d73f",String.class)).contains("error");
    }

    @Test
    public void getTeam() {
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/team/635b589952da8e11048f3cb0",String.class)).contains("error");
    }

    @Test
    public void addTeam() {
        Team team = new Team();
        team.setName("blah");
        assertThat(this.restTemplate.postForObject("http://localhost:" +port+"/team",team,String.class)).contains("office");
    }

}
