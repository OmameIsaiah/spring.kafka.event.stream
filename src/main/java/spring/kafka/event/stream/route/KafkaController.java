package spring.kafka.event.stream.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.kafka.event.stream.dto.request.EmailDto;
import spring.kafka.event.stream.dto.request.SmsDto;
import spring.kafka.event.stream.dto.response.ApiResponse;
import spring.kafka.event.stream.producer.MessageProducer;
import spring.kafka.event.stream.utils.MessageUtils;

import java.util.Objects;

import static spring.kafka.event.stream.utils.MessageUtils.*;

@RestController
@RequestMapping("/api/v1")
public class KafkaController {

    @Autowired
    private MessageProducer messageProducer;

    @PostMapping("/test/send")
    public ResponseEntity sendMessage(@RequestParam("message") String message) {
        if (Objects.isNull(message)) {
            return ResponseEntity.badRequest().body(new
                    ApiResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    MessageUtils.FAILED,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid request parameters, please check!"));
        }
        String result = messageProducer.sendMessage(GENERAL_TOPIC, message);
        return ResponseEntity.ok().body(new
                ApiResponse(
                HttpStatus.OK.name(),
                MessageUtils.SUCCESS,
                HttpStatus.OK.value(),
                result));

    }

    @PostMapping("/email/send")
    public ResponseEntity sendEmailEvent(@RequestBody EmailDto emailDto) {
        if (Objects.isNull(emailDto)) {
            return ResponseEntity.badRequest().body(new
                    ApiResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    MessageUtils.FAILED,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid request parameters, please check!"));
        }
        String result = messageProducer.sendMessage(EMAIL_TOPIC, emailDto);
        return ResponseEntity.ok().body(new
                ApiResponse(
                HttpStatus.OK.name(),
                MessageUtils.SUCCESS,
                HttpStatus.OK.value(),
                result));
    }

    @PostMapping("/sms/send")
    public ResponseEntity sendSMSEvent(@RequestBody SmsDto smsDto) {
        if (Objects.isNull(smsDto)) {
            return ResponseEntity.badRequest().body(new
                    ApiResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    MessageUtils.FAILED,
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid request parameters, please check!"));
        }
        String result = messageProducer.sendMessage(SMS_TOPIC, smsDto);
        return ResponseEntity.ok().body(new
                ApiResponse(
                HttpStatus.OK.name(),
                MessageUtils.SUCCESS,
                HttpStatus.OK.value(),
                result));
    }
}