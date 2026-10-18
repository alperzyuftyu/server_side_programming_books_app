package be.thomasmore.bookserver.controllers;

import be.thomasmore.bookserver.model.dto.MemberDetailedDTO;
import be.thomasmore.bookserver.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "get one member from database by id",
    description = "if request parameter <b>ID</b> is given " +
            "returns member id,username,firstname,lastname and favorite books")

    @GetMapping("/{id:\\d+}")
    public MemberDetailedDTO findById(@PathVariable int id) {
        return memberService.findById(id);

    }

    @Operation(summary = "get one member from database by username",
            description = "if request parameter <b>username</b> is given " +
                    "returns member id,username,firstname,lastname and favorite books")
    @GetMapping("{name:.*[a-zA-Z].*}")
    public MemberDetailedDTO findByName(@PathVariable String name) {
        return memberService.findByUsername(name);
    }

    @Operation(summary = "get the loggedin member from database",
            description = "if member is logged in " +
                    "returns member id,username,firstname,lastname and favorite books")

    @GetMapping("/me")
    public MemberDetailedDTO findCurrentMember(Authentication authentication) {
        String username = authentication.getName();

        return memberService.findByUsername(username);
    }
}
