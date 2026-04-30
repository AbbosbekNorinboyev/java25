package uz.brb.java25.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import uz.brb.java25.config.RabbitMQConfig;
import uz.brb.java25.dto.RabbitDto;

@Service
@RequiredArgsConstructor
public class Producer {
    private final RabbitTemplate rabbitTemplate;

    public void send(RabbitDto dto) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.GATE_EXCHANGE,
                RabbitMQConfig.RK_REQ,
                dto);
        System.out.println("📤 Message sent: " + dto);
    }
}
