package kr.co.dwebss.kococo.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = {"kr.co.dwebss.kococo.core.entities"})
@EnableJpaRepositories("kr.co.dwebss.kococo.core.repository")
public class KococoApiApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "/app/config/kococo_api/real-application.yml";
    
	public static void main(String[] args) {
		SpringApplication.run(KococoApiApplication.class, args);
	}

}
