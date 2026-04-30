package uz.brb.java25.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import uz.brb.java25.config.RabbitMQConfig;
import uz.brb.java25.dto.RabbitDto;

@Component
public class Consumer {
    @RabbitListener(queues = RabbitMQConfig.REQ_Q)
    public void receive(RabbitDto dto) {
        System.out.println("📥 Message received: " + dto);
    }
}
