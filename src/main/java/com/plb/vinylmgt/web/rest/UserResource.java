package com.plb.vinylmgt.web.rest;

import com.plb.vinylmgt.entity.User;
import com.plb.vinylmgt.error.UserNotFoundException;
import com.plb.vinylmgt.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("get-by-id")
    public ResponseEntity<User> get(@RequestParam UUID id) {
        Optional<User> userById = userService.getById(id);
        return userById.map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User newUser) {
        return ResponseEntity.ok(userService.save(newUser));
    }

    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User updateUser) {
        return ResponseEntity.ok(userService.save(updateUser));
    }

    @DeleteMapping()
    public ResponseEntity<User> delete(@RequestParam UUID id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException erdae) {
            ProblemDetail userNotFound = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "User not found");
            return ResponseEntity.of(userNotFound).build();
        }
    }
}
