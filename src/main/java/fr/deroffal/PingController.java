package fr.deroffal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        LOGGER.info("Démarrage de l'appli OK !");
        return new ResponseEntity<String>("Pong", HttpStatus.OK);
    }
}
