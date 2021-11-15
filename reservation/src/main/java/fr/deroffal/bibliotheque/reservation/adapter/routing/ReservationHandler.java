package fr.deroffal.bibliotheque.reservation.adapter.routing;

import fr.deroffal.bibliotheque.reservation.domain.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ReservationHandler {

    private static final Mono<ServerResponse> NOT_FOUND = ServerResponse.notFound().build();

    private final ReservationService reservationService;

    public Mono<ServerResponse> handle(final ServerRequest request) {
        return Mono.just(request.pathVariable("uuid"))
            .map(reservationService::getById)
            .flatMap(ReservationHandler::ok)
            .switchIfEmpty(NOT_FOUND);
    }

    private static Mono<ServerResponse> ok(final Object reservation) {
        return ServerResponse.ok().body(BodyInserters.fromValue(reservation));
    }
}
