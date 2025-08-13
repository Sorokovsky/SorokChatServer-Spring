package pro.sorokovsky.sorokchatserverspring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pro.sorokovsky.sorokchatserverspring.anotation.response.*;
import pro.sorokovsky.sorokchatserverspring.contract.GetUserPayload;
import pro.sorokovsky.sorokchatserverspring.contract.LoginPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewUserPayload;
import pro.sorokovsky.sorokchatserverspring.mapper.UserMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.service.AuthenticationService;

@RestController
@RequestMapping("authentication")
@RequiredArgsConstructor
@Tag(name = "Автентифікація")
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserMapper mapper;

    @GetMapping("get-me")
    @Operation(summary = "Отримання авторизованого користувача")
    @ApiOkWithUserResponse
    @ApiUnauthorizedResponse
    @ApiBadRequestResponse
    @ApiInternalServerErrorResponse
    public ResponseEntity<GetUserPayload> getMe(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.ok(mapper.toGet(user));
    }

    @PostMapping("register")
    @Operation(summary = "Реєстрація користувача")
    @ApiRegisteredResponse
    @ApiBadRequestResponse
    @ApiInternalServerErrorResponse
    @ApiDeniedAccessResponse
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
    @Operation(summary = "Авторизація")
    @ApiOkWithUserResponse
    @ApiInternalServerErrorResponse
    @ApiDeniedAccessResponse
    public ResponseEntity<GetUserPayload> login(@Valid @RequestBody LoginPayload payload) {
        final var user = service.login(payload);
        return ResponseEntity.ok(mapper.toGet(user));
    }

    @Operation(summary = "Вихід з акаунту")
    @ApiUnauthorizedResponse
    @ApiInternalServerErrorResponse
    @ApiBadRequestResponse
    @ApiNoContentResponse
    @DeleteMapping("logout")
    public ResponseEntity<Void> logout() {
        service.logout();
        return ResponseEntity.noContent().build();
    }

}
