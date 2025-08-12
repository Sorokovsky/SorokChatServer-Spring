package pro.sorokovsky.sorokchatserverspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sorokovsky.sorokchatserverspring.entity.ChannelEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelsRepository extends CrudRepository<ChannelEntity, Long> {
    List<ChannelEntity> findAllByUsersId(Long usersId);

    Optional<ChannelEntity> findById(long id);

    ChannelEntity save(ChannelEntity channel);

    void delete(ChannelEntity channel);
}
