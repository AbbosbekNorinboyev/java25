package uz.brb.java25;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableRabbit
@ConfigurationPropertiesScan
public class Java25Application {

    static void main(String[] args) {
        SpringApplication.run(Java25Application.class, args);
    }

}
