package spring.kafka.event.stream.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import spring.kafka.event.stream.dto.request.QueueRequest;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {

    private final ObjectMapper objectMapper;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(String topic, Object message) {
        QueueRequest request = QueueRequest.builder()
                .type(topic)
                .data(message)
                .build();
        String content = "";
        try {
            content = objectMapper.writeValueAsString(request);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error writing content: {}" + e.getMessage());
        }
        log.info("######################### Event Content: {}", content);
        //kafkaTemplate.send(topic, content);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, content);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
        return "Event sent successfully!";
    }

}