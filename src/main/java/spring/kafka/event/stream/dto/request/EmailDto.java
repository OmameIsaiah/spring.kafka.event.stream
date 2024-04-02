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
public class EmailDto implements Serializable {
    private String uuid;
    private String email;
    private String senderName;
    private String subject;
    private String message;

    @Override
    public String toString() {
        return "EmailDto{" +
                "uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", senderName='" + senderName + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
