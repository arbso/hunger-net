package com.lufthansa.backend.service;


import com.lufthansa.backend.AbstractTest;
import com.lufthansa.backend.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import com.lufthansa.backend.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureDataJpa
public class UserServiceTest extends AbstractTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void saveUserTest() {

        Integer id = 1;
        User user1 = new User();
        user1.setId(id);
        user1.setUsername("Filani");
        user1.setPassword("1");
        user1.setEmail("filani@gmail.com");
        userRepository.save(user1);
        Assertions.assertThat(user1.getId()).isGreaterThan(0);
    }

    @Test
    public void getUserTest() {
        Integer id = 1;
        User user1 = new User();
        user1.setId(id);
        user1.setUsername("Filani");
        user1.setPassword("1");
        user1.setEmail("filani@gmail.com");
        User user = userRepository.findById(1).orElse(null);

        Assertions.assertThat(user.getId()).isEqualTo(1);

    }
}