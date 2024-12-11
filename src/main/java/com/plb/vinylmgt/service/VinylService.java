package com.plb.vinylmgt.service;

import com.plb.vinylmgt.entity.Author;
import com.plb.vinylmgt.entity.Vinyl;
import com.plb.vinylmgt.error.VinylNotFoundException;
import com.plb.vinylmgt.repository.VinylRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VinylService {

    private final VinylRepository vinylRepository;
    private final AuthorService authorService;

    public VinylService(VinylRepository vinylRepository, AuthorService authorService) {
        this.vinylRepository = vinylRepository;
        this.authorService = authorService;
    }

    @Transactional(readOnly = true)
    public List<Vinyl> getAll() {
        return vinylRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vinyl> getVinylsByUser(String email) {
        return vinylRepository.findAllByUserEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Vinyl> getById(UUID id) {
        return vinylRepository.findById(id);
    }

    @Transactional
    public Vinyl save(Vinyl vinyl) {
        Author newAuthor = this.authorService.save(vinyl.getAuthor());
        vinyl.setAuthor(newAuthor);
        return vinylRepository.save(vinyl);
    }

    @Transactional
    public void delete(UUID id) {
        long nbOfDeletedVinyls = vinylRepository.deleteByIdWithCount(id);
        if (nbOfDeletedVinyls == 0) {
            throw new VinylNotFoundException("Vinyl not found");
        }
    }
}
