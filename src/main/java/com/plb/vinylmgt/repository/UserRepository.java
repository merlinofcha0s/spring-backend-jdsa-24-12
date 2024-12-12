
package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Modifying
    @Query(value = "DELETE FROM User u where u.id = :uuid")
    int deleteByIdWithCount(UUID uuid);

    Optional<User> findOneByEmailEqualsIgnoreCase(String email);

}
