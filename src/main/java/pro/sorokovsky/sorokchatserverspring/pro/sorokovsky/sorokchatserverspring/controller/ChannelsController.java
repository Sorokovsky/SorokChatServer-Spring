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
import pro.sorokovsky.sorokchatserverspring.contract.GetChannelPayload;
import pro.sorokovsky.sorokchatserverspring.contract.NewChannelPayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateChannelPayload;
import pro.sorokovsky.sorokchatserverspring.exception.UserNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.ChannelMapper;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.service.ChannelsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("channels")
@RequiredArgsConstructor
@Tag(name = "Чати")
public class ChannelsController {
    private final ChannelsService service;
    private final ChannelMapper mapper;

    @GetMapping("by-user/{userId:\\d++}")
    @Operation(summary = "Отримати чати за користувачем")
    @ApiUnauthorizedResponse
    @ApiInternalServerErrorResponse
    @ApiBadRequestResponse
    @ApiOkWithChannelListResponse
    public ResponseEntity<List<GetChannelPayload>> getChannels(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(service.getByUserId(userId).stream().map(mapper::toGet).collect(Collectors.toList()));
    }

    @Operation(summary = "Отримання чату за унікальним ідентифікатором")
    @ApiOkWithChannelResponse
    @ApiInternalServerErrorResponse
    @ApiBadRequestResponse
    @ApiUnauthorizedResponse
    @GetMapping("by-id/{id:\\d++}")
    public ResponseEntity<GetChannelPayload> getChannel(@PathVariable("id") long id) {
        final var channel = service.getById(id).orElseThrow(UserNotFoundException::new);
        return ResponseEntity.ok(mapper.toGet(channel));
    }

    @PostMapping
    public ResponseEntity<GetChannelPayload> create(
            @AuthenticationPrincipal UserModel user,
            @Valid @RequestBody NewChannelPayload payload,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        final var created = service.create(user, payload);
        return ResponseEntity
                .created(
                        uriComponentsBuilder
                                .pathSegment("channels/%d".formatted(created.getId()))
                                .build()
                                .toUri()
                )
                .body(mapper.toGet(created));
    }

    @PutMapping("by-id/{id:\\d++}")
    public ResponseEntity<GetChannelPayload> update(@PathVariable("id") long id, @Valid @RequestBody UpdateChannelPayload payload) {
        return ResponseEntity.ok(mapper.toGet(service.update(id, payload)));
    }

    @PutMapping("add-user/{id:\\d++}/{userId:\\d++}")
    public ResponseEntity<GetChannelPayload> addUser(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        return ResponseEntity.ok(mapper.toGet(service.addUser(id, userId)));
    }

    @PutMapping("remove-user/{id:\\d++}/{userId:\\d++}")
    public ResponseEntity<GetChannelPayload> removeUser(@PathVariable("id") long id, @PathVariable("userId") long userId) {
        return ResponseEntity.ok(mapper.toGet(service.removeUser(id, userId)));
    }

    @DeleteMapping("by-id/{id:\\d++}")
    public ResponseEntity<GetChannelPayload> delete(@PathVariable("id") long id) {
        return ResponseEntity.ok(mapper.toGet(service.delete(id)));
    }
}
