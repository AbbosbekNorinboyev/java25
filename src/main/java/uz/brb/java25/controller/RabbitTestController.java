package uz.brb.java25.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.brb.java25.dto.RabbitDto;
import uz.brb.java25.producer.Producer;

@RestController
@RequestMapping("/rabbit")
@RequiredArgsConstructor
public class RabbitTestController {

    private final Producer producer;

    @PostMapping("/send")
    public String send(@RequestBody RabbitDto dto) {
        producer.send(dto);
        return "Message sent!";
    }
}