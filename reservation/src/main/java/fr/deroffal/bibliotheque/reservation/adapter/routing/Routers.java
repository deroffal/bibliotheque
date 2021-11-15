package fr.deroffal.bibliotheque.reservation.adapter.routing;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration(proxyBeanMethods = false)
public class Routers {

    @Bean
    public RouterFunction<ServerResponse> reservationRoutes(final ReservationHandler reservationHandler) {
        //@formatter:off
        return route()
            .GET("/reservation/{uuid}", accept(MediaType.APPLICATION_JSON), reservationHandler::handle)
            .build();
        //@formatter:on
    }
}
