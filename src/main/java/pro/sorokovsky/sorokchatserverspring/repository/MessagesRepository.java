package pro.sorokovsky.sorokchatserverspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sorokovsky.sorokchatserverspring.entity.MessageEntity;

@Repository
public interface MessagesRepository extends CrudRepository<MessageEntity, Long> {
    MessageEntity save(MessageEntity message);

    MessageEntity findById(long id);

    void deleteById(Long id);
}
