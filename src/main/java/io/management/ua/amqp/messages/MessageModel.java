package io.management.ua.amqp.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {
    @NotBlank(message = "Sender address can not be blank")
    private String sender;
    @NotBlank(message = "Receiver address can not be blank")
    private String receiver;
    @NotBlank(message = "Message content can not be blank")
    private String content;
    @NotBlank(message = "Subject of the message can not be blank")
    private String subject;
    private ZonedDateTime sendingDate;
    private MessageType messageType;

    public enum MessageType {
        PLAIN_TEXT, HTML, WITH_FILE
    }
}
