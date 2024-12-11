
package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
