package pro.sorokovsky.sorokchatserverspring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pro.sorokovsky.sorokchatserverspring.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(Long id);

    UserEntity save(UserEntity userEntity);

    void deleteById(Long id);
}
