package com.plb.vinylmgt.web.rest;

import com.plb.vinylmgt.entity.User;
import com.plb.vinylmgt.error.UserNotFoundException;
import com.plb.vinylmgt.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "User not found") })
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
