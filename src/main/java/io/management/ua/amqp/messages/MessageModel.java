package io.management.ua.amqp.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageModel {
    private String sender;
    private String receiver;
    private String content;
    private String subject;
    private ZonedDateTime sendingDate;
}
