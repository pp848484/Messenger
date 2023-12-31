package com.chernyshev.messenger.store.repositories;

import com.chernyshev.messenger.store.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Long> {

    @Query("""
    SELECT t from TokenEntity  t inner join UserEntity u on t.user.id = u.id
    where u.id=:userId and (t.expired=false or t.revoked=false)
    """)
    Optional<List<TokenEntity>> findAllValidTokensByUser(Long userId);
    Optional<TokenEntity> findByToken(String token);

}
