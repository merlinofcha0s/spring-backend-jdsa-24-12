package com.plb.vinylmgt;

import com.plb.vinylmgt.repository.AuthorRepository;
import com.plb.vinylmgt.repository.UserRepository;
import com.plb.vinylmgt.repository.VinylRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBCleaner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VinylRepository vinylRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void clearAllTables() {
        vinylRepository.deleteAll();
        userRepository.deleteAll();
        authorRepository.deleteAll();
    }
}
