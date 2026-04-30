package uz.brb.java25.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfig {

    // exchange
    public static final String GATE_EXCHANGE = "gate.exchange";

    // dead letter exchange
    public static final String DLX = "gate.dlx";

    // request queue
    public static final String REQ_Q = "gate.open.request.q";

    // response queue
    public static final String RES_Q = "gate.open.result.q";

    // request dlq
    public static final String REQ_DLQ = "gate.open.request.dlq";

    // response dlq
    public static final String RES_DLQ = "gate.open.result.dlq";

    // routing key
    public static final String RK_REQ = "gate.open.request";
    public static final String RK_RES = "gate.open.result";

    // ROUTING KEYS OF req/res dead letter queue
    public static final String RK_REQ_DLQ = "gate.open.request.dlq";
    public static final String RK_RES_DLQ = "gate.open.result.dlq";


    @Bean
    public DirectExchange gateExchange() {
        return new DirectExchange(GATE_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange gateDlx() {
        return new DirectExchange(DLX, true, false);
    }

    @Bean
    public Queue gateRequestQueue() {
        return QueueBuilder.durable(REQ_Q)
                .deadLetterExchange(DLX)
                .deadLetterRoutingKey(RK_REQ_DLQ)
                .build();
    }

    @Bean
    public Queue gateResponseQueue() {
        return QueueBuilder.durable(RES_Q)
                .deadLetterExchange(DLX)
                .deadLetterRoutingKey(RK_RES_DLQ)
                .build();
    }

    @Bean
    public Queue gateRequestDlq() {
        return QueueBuilder.durable(REQ_DLQ).build();
    }

    @Bean
    public Queue gateResponseDlq() {
        return QueueBuilder.durable(RES_DLQ).build();
    }

    @Bean
    public Binding bindGateRequest(@Qualifier("gateRequestQueue") Queue gateRequestQueue,
                                   @Qualifier("gateExchange") DirectExchange gateExchange) {
        return BindingBuilder.bind(gateRequestQueue).to(gateExchange).with(RK_REQ);
    }

    @Bean
    public Binding bindGateResponse(@Qualifier("gateResponseQueue") Queue gateResponseQueue,
                                    @Qualifier("gateExchange") DirectExchange gateExchange) {
        return BindingBuilder.bind(gateResponseQueue).to(gateExchange).with(RK_RES);
    }

    @Bean
    public Binding bindGateRequestDlq(@Qualifier("gateRequestDlq") Queue gateRequestDlq,
                                      @Qualifier("gateDlx") DirectExchange gateDlx) {
        return BindingBuilder.bind(gateRequestDlq).to(gateDlx).with(RK_REQ_DLQ);
    }

    @Bean
    public Binding bindGateResponseDlq(@Qualifier("gateResponseDlq") Queue gateResponseDlq,
                                       @Qualifier("gateDlx") DirectExchange gateDlx) {
        return BindingBuilder.bind(gateResponseDlq).to(gateDlx).with(RK_RES_DLQ);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("navoiy-queue", true);
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange("chemical-exchange");
    }

    @Bean
    public Binding binding(@Qualifier("notificationQueue") Queue notificationQueue,
                           @Qualifier("notificationExchange") DirectExchange notificationExchange) {
        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with("NewLessonPublishedEvent");
    }

    @Bean(name = "deadLetterExchange")
    public DirectExchange deadLetterExchange() {
        return new DirectExchange("dead-letter-exchange");
    }

    @Bean(name = "deadLetterBinding")
    public Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue deadLetterQueue,
                                     @Qualifier("deadLetterExchange") DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("deadLetterRoutingKey");
    }

    @Bean(name = "deadLetterQueue")
    public Queue deadLetterQueue() {
        return new Queue("dead-letter-queue", true);
    }
}