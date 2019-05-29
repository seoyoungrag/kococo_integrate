package kr.co.dwebss.kococo.core;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.dwebss.kococo.core.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KococoCoreTest {

	@Autowired
	UserRepository userRepository;

    @Test
    public void contextLoads() {
        assertThat(userRepository.findAll()).isNotNull();
    }

    @SpringBootApplication
    static class TestConfiguration {
    }
}
