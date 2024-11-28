package com.main.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
@Component
public class RouteValidator {
    public  static final List<String>openEndPoints=List.of(
            "/api/auth/**",
            "/eureka"
    );
    public Predicate<ServerHttpRequest> isSecured=
            serverHttpRequest -> openEndPoints
                    .stream()
                    .noneMatch(uri->serverHttpRequest.getURI().getPath().contains(uri));
}
