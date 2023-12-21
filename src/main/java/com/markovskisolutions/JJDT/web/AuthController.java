package com.markovskisolutions.JJDT.web;

import com.markovskisolutions.JJDT.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Operation(summary = "Generate api token.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response is the generated API token, valid for 1 hour.",
                    content = @Content(
                            mediaType = "String",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Return when user do not pass correct user name and password for authentication.",
                    content = @Content
            )
    })
    @PostMapping("/api/token")
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }


    @PostMapping("/api/token/{username}")
    public String token(@PathVariable String username, Authentication authentication) {
        return tokenService.generateToken(authentication);
    }
}
