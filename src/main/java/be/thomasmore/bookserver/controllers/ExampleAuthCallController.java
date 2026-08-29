package be.thomasmore.bookserver.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@Slf4j
public class ExampleAuthCallController {


    @Operation(summary = "example of an authenticated GET request.",
            description = "This request will only succeed if a user is authenticated. ")
    @GetMapping("/example_auth_request")
    public String authenticatedRequest(Principal principal) {
        log.info("##### example authenticated request: ");
        return "example authenticated request: This request will only succeed if a user is authenticated. ";
    }

}
