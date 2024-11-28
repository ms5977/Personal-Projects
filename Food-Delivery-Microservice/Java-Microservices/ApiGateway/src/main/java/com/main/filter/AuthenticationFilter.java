package com.main.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final Logger logger= LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    private final Map<String, List<String>>routesRoles=Map.of(
            "/api/customer/**",List.of("ADMIN_ROLE"),
            "/api/restaurant/create",List.of("ADMIN_ROLE"),
                "/api/restaurant/getAll",List.of("USER_ROLE")
    );

    public  AuthenticationFilter(){
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                logger.info("Validating request for secured route: {}", exchange.getRequest().getURI());

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    logger.error("Missing Authorization header for request: {}", exchange.getRequest().getURI());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return sendErrorResponse(exchange,HttpStatus.UNAUTHORIZED,"Authorization header is missing");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    // Validate Token
                    jwtUtil.validateToken(authHeader);

                    //Extract Roles
                    List<String> userRoles = jwtUtil.extractRoles(authHeader);

                    //Get required roles for the route
                    String requestPath = exchange.getRequest().getPath().toString();
                    List<String> requiredRoles = routesRoles.entrySet().stream()
                            .filter(entry -> requestPath.matches(entry.getKey().replace("**", ".*")))
                            .map(Map.Entry::getValue)
                            .findFirst()
                            .orElse(List.of());

                    //Check if user has required role
                    if (!requiredRoles.isEmpty() && requiredRoles.stream().noneMatch(userRoles::contains)){
                        logger.error("User does not have required roles for route: {}",requestPath);
                        return  sendErrorResponse(exchange,HttpStatus.FORBIDDEN,"User does not have the required roles to access the resource");
                    }
                } catch (Exception e) {
                    logger.error("Invalid token for request: {}", exchange.getRequest().getURI(), e);
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return sendErrorResponse(exchange,HttpStatus.UNAUTHORIZED,"Invalid or expired token");
                }
            }
            return chain.filter(exchange);
        });

    }
    private Mono<Void>sendErrorResponse(ServerWebExchange exchange,HttpStatus status,String message){
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Map<String,Object>errorResponse=Map.of(
                "status",status.value(),
                "error",status.getReasonPhrase(),
                "message",message,
                "path",exchange.getRequest().getPath().toString()
        );
        try {
            byte[] bytes = new ObjectMapper().writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            logger.error("Error writing custom error response",e);
            return exchange.getResponse().setComplete();
        }
    }
    public static class Config {
        // Add configuration properties if needed
    }
}
