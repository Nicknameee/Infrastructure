package io.management.manager;

import io.management.ua.response.Response;
import io.management.ua.utility.enums.Locale;
import io.management.ua.utility.enums.WebSocketTopics;
import io.management.ua.utility.models.UserSecurityRole;
import io.management.ua.utility.models.UserSecurityStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/management")
@Slf4j
@PreAuthorize("hasRole(T(io.management.ua.utility.models.UserSecurityRole).ROLE_MANAGER)")
@ConditionalOnProperty(prefix = "spring.application", name = "name", havingValue = "User")
public class ManagerController {
    @GetMapping("/available/roles")
    public Response<?> getAvailableUserRoles() {
        return Response.ok(UserSecurityRole.values());
    }

    @GetMapping("/available/locales")
    public Response<?> getAvailableUserLocales() {
        return Response.ok(Locale.values());
    }

    @GetMapping("/available/user/statuses")
    public Response<?> getAvailableUserStatuses() {
        return Response.ok(UserSecurityStatus.values());
    }

    @GetMapping("/available/websocket/topics")
    public Response<?> getAvailableWebSocketTopics() {
        return Response.ok(WebSocketTopics.values());
    }
}
