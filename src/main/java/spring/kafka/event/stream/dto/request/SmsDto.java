package spring.kafka.event.stream.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SmsDto implements Serializable {
    private String uuid;
    private String phone;
    private String message;

    @Override
    public String toString() {
        return "SmsDto{" +
                "uuid='" + uuid + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
