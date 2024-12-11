package com.plb.vinylmgt.web.rest;

import com.plb.vinylmgt.entity.Vinyl;
import com.plb.vinylmgt.service.VinylService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/vinyls")
public class VinylResource {

    private final VinylService vinylService;

    public VinylResource(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping
    public ResponseEntity<List<Vinyl>> getAll() {
        return ResponseEntity.ok(vinylService.getAll());
    }

    @PostMapping
    public ResponseEntity<Vinyl> save(@Valid @RequestBody Vinyl vinyl) {
        return ResponseEntity.ok(vinylService.save(vinyl));
    }

    @GetMapping("/by-id")
    public ResponseEntity<Vinyl> getById(@RequestParam UUID uuid) {
        Optional<Vinyl> vinylById = vinylService.getById(uuid);
        return vinylById.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    ProblemDetail vinylNotFound = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Vinyl Not found");
                    return ResponseEntity.of(vinylNotFound).build();
                });
    }

    @PutMapping
    public ResponseEntity<Vinyl> update(@RequestParam UUID uuid, @Valid @RequestBody Vinyl vinylToUpdate) {
        vinylToUpdate.setId(uuid);
        Vinyl updatedVinyl = vinylService.save(vinylToUpdate);
        return ResponseEntity.ok(updatedVinyl);
    }

    @DeleteMapping
    public ResponseEntity<Vinyl> delete(@RequestParam UUID uuid) {
        vinylService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}
