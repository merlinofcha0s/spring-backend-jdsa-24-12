
package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VinylRepository extends JpaRepository<Vinyl, UUID> {

    @Modifying
    @Query(value = "DELETE FROM Vinyl v where v.id = :uuid")
    int deleteByIdWithCount(UUID uuid);

    List<Vinyl> findAllByUserEmail(String email);
}
