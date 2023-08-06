package io.management.ua.amqp.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApprovalMessage {
    private String email;
    private boolean isApproved;
}
