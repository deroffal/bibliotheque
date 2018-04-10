package fr.deroffal.portail.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "/public", description = "Contrôleur public")
@RestController
@RequestMapping(value = "/public")
public class PingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

    @ApiOperation(
            httpMethod = "GET",
            value = "Un ping du serveur"
    )
    @GetMapping(value = "/ping")
    public ResponseEntity<String> ping() {
        LOGGER.info("OK !");
        return new ResponseEntity<String>("Pong", HttpStatus.OK);
    }
}
