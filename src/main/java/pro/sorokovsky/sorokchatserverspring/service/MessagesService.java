package pro.sorokovsky.sorokchatserverspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sorokovsky.sorokchatserverspring.contract.NewMessagePayload;
import pro.sorokovsky.sorokchatserverspring.contract.UpdateMessagePayload;
import pro.sorokovsky.sorokchatserverspring.exception.message.MessageNotFoundException;
import pro.sorokovsky.sorokchatserverspring.mapper.MessageMapper;
import pro.sorokovsky.sorokchatserverspring.model.MessageModel;
import pro.sorokovsky.sorokchatserverspring.model.UserModel;
import pro.sorokovsky.sorokchatserverspring.repository.MessagesRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagesService {
    private final MessageMapper mapper;
    private final MessagesRepository repository;

    @Transactional(readOnly = true)
    public Optional<MessageModel> getById(Long id) {
        return repository.findById(id).map(mapper::toModel);
    }

    @Transactional
    public MessageModel create(UserModel author, NewMessagePayload payload) {
        return mapper.toModel(repository.save(mapper.toEntity(payload, author)));
    }

    @Transactional
    public MessageModel update(Long id, UpdateMessagePayload payload) {
        final var message = getById(id).orElseThrow(MessageNotFoundException::new);
        final var merged = mapper.merge(message, payload);
        return mapper.toModel(repository.save(merged));
    }

    @Transactional
    public MessageModel delete(Long id) {
        final var message = getById(id).orElseThrow(MessageNotFoundException::new);
        repository.deleteById(id);
        return message;
    }
}
