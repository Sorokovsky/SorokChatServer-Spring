package pro.sorokovsky.sorokchatserverspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sorokovsky.sorokchatserverspring.contract.GetUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.LoginPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewUserPayload;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.service.AuthenticationService;

import java.util.HashMap;

@RestController
@RequestMapping("authentication")
@RequiredArgsConstructor
@Tag(name = "Автентифікація")
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserMapper mapper;

    @GetMapping("get-me")
    @Operation(summary = "Отримати авторизованого користувача")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Успішно",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GetUserPayload.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Неавторизовано",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "Не правильні данні",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HashMap.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Невідома помилка",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HashMap.class)
                            )
                    )
            }
    )
    public ResponseEntity<GetUserPayload> getMe(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.ok(mapper.toGet(user));
    }

    @PostMapping("register")
    @Operation(summary = "Реєстрація користувача")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GetUserPayload.class)
                            ),
                            responseCode = "201",
                            description = "Створено",
                            headers = {
                                    @Header(
                                            name = HttpHeaders.LOCATION,
                                            required = true,
                                            description = "Посилання на авторизованого користувача",
                                            example = "/authentication/get-me"
                                    ),
                                    @Header(
                                            name = HttpHeaders.AUTHORIZATION,
                                            required = true,
                                            description = "Токен доступу",
                                            example = "Bearer <token>"
                                    ),
                            }
                    ),
                    @ApiResponse(
                            description = "Не правильні данні",
                            responseCode = "400",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HashMap.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Невідома помилка",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HashMap.class)
                            )
                    ),
                    @ApiResponse(
                            description = "В доступі відмовлено",
                            responseCode = "403"
                    )
            }
    )
    public ResponseEntity<GetUserPayload> register(
            @Valid @RequestBody NewUserPayload payload,
            UriComponentsBuilder uriBuilder
    ) {
        final var created = service.register(payload);
        return ResponseEntity
                .created(uriBuilder.pathSegment("authentication/get-me").build().toUri())
                .body(mapper.toGet(created));
    }

    @PutMapping("login")
    public ResponseEntity<GetUserPayload> login(@Valid @RequestBody LoginPayload payload) {
        final var user = service.login(payload);
        return ResponseEntity.ok(mapper.toGet(user));
    }

    @DeleteMapping("logout")
    public ResponseEntity<Void> logout() {
        service.logout();
        return ResponseEntity.noContent().build();
    }

}
