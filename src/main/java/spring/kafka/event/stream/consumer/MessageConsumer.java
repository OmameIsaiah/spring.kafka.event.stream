
package spring.kafka.event.stream.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import spring.kafka.event.stream.dto.request.EmailDto;
import spring.kafka.event.stream.dto.request.QueueRequest;
import spring.kafka.event.stream.dto.request.SmsDto;
import spring.kafka.event.stream.utils.MessageUtils;
import spring.kafka.event.stream.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static spring.kafka.event.stream.utils.MessageUtils.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "" + GENERAL_TOPIC + "", groupId = "my-group-id")
    public void generalTopicConsumer(String message) {
        log.info("Received message: " + message);
    }

    @KafkaListener(topics = "" + EMAIL_TOPIC + "", groupId = "my-group-id")
    public void emailTopicConsumer(String message) {
        log.info("######################### Event Received: {}", message);
        if (Objects.nonNull(message)) {
            try {
                QueueRequest request = Utils.getGson().fromJson(message, QueueRequest.class);
                log.info("VALUE AFTER CONVERSION: {}", request);
                log.info("MESSAGE TOPIC: {}", request.getType());
                String data = objectMapper.writeValueAsString(request.getData());
                EmailDto product = Utils.getGson().fromJson(data, EmailDto.class);
                log.info("*******************************PROCESSING EMAIL FROM TOPIC: {}", product.toString());
            } catch (Exception e) {
                log.error("Error converting data", e.getMessage());
                e.printStackTrace();
            }
        } else {
            log.warn("Oops! Invalid message received!");
        }
    }

    @KafkaListener(topics = "" + SMS_TOPIC + "", groupId = "my-group-id")
    public void smsTopicConsumer(String message) {
        log.info("######################### Event Received: {}", message);
        if (Objects.nonNull(message)) {
            try {
                QueueRequest request = Utils.getGson().fromJson(message, QueueRequest.class);
                log.info("VALUE AFTER CONVERSION: {}", request);
                log.info("MESSAGE TOPIC: {}", request.getType());
                String data = objectMapper.writeValueAsString(request.getData());
                SmsDto product = Utils.getGson().fromJson(data, SmsDto.class);
                log.info("*******************************PROCESSING SMS FROM TOPIC: {}", product.toString());
            } catch (Exception e) {
                log.error("Error converting data", e.getMessage());
                e.printStackTrace();
            }
        } else {
            log.warn("Oops! Invalid message received!");
        }
    }


    public String decodeByte(byte[] byteArray) {
        // Decode byte array to string using UTF-8 character set
        return new String(byteArray, StandardCharsets.UTF_8);
    }
}