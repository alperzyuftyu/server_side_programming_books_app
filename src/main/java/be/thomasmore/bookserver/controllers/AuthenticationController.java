package be.thomasmore.bookserver.controllers;

import be.thomasmore.bookserver.model.Member;
import be.thomasmore.bookserver.model.converters.MemberDTOConverter;
import be.thomasmore.bookserver.model.dto.AuthenticationDTO;
import be.thomasmore.bookserver.model.dto.UserDTO;
import be.thomasmore.bookserver.repositories.MemberRepository;
import jakarta.validation.Valid;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

/**
 * REST controller for authentication operations.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final MemberDTOConverter memberDTOConverter;

    public AuthenticationController(JdbcUserDetailsManager jdbcUserDetailsManager,
                                    PasswordEncoder passwordEncoder,
                                    MemberRepository memberRepository,
                                    MemberDTOConverter memberDTOConverter) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
        this.memberDTOConverter = memberDTOConverter;
    }

    @GetMapping("/authenticate")
    public AuthenticationDTO authenticate(Principal principal) {
        log.info("##### authenticate");
        String username = principal != null ? principal.getName() : "anonymous";
        return new AuthenticationDTO(username);
    }

    @PostMapping("/signup")
    public AuthenticationDTO signup(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) throws ServletException {
        log.info("##### signup {}", userDTO.username());

        if (jdbcUserDetailsManager.userExists(userDTO.username())) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "User with name %s already exists.".formatted(userDTO.username()));
        }

        UserDetails user = User.withUsername(userDTO.username())
                .password(passwordEncoder.encode(userDTO.password()))
                .authorities("USER")
                .build();
        jdbcUserDetailsManager.createUser(user);

        Member member = memberDTOConverter.convertToEntity(userDTO);
        memberRepository.save(member);

        request.login(userDTO.username(), userDTO.password());

        return new AuthenticationDTO(userDTO.username());
    }
}
