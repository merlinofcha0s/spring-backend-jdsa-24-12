
package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.Vinyl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VinylRepository extends JpaRepository<Vinyl, UUID> {
}
