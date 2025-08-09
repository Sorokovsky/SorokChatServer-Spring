package pro.sorokovsky.sorokchatserverspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UserMapper mapper;

    @GetMapping("get-me")
    public ResponseEntity<GetUserPayload> getMe(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.ok(mapper.toGet(user));
    }

    @PostMapping("register")
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
