package com.plb.vinylmgt.service;

import com.plb.vinylmgt.entity.Author;
import com.plb.vinylmgt.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> getAll(){
        return authorRepository.findAll();
    }

    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
