package io.management.ua.amqp.models.users;

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
