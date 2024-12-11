package com.plb.vinylmgt.repository;

import com.plb.vinylmgt.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {}
