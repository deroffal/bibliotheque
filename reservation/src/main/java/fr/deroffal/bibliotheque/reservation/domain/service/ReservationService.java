package fr.deroffal.bibliotheque.reservation.domain.service;

import fr.deroffal.bibliotheque.reservation.domain.model.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    public Reservation getById(final String uuid) {
        return new Reservation(uuid);
    }
}
